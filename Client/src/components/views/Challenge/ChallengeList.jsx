import React, { useState, useEffect } from "react";
import "./ChallengeList.css";
import history from "../../../history/history";
import Skeleton, { SkeletonTheme } from "react-loading-skeleton";
import { useDispatch, useSelector } from "react-redux";
import { get } from "../../../utils/axios/axiosManage";
import { UpdateChallenge } from "../../../_actions/ChallengeAction";

const RenderChallengeLoadingList = () => {
    const Loading = [];
    for (let i = 0; i < 10; i++) {
        Loading.push(
            <div className="ChallengeCard" key={i}>
                <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                    <Skeleton width="90%px" height="20vh" duration={2} />
                </SkeletonTheme>
                <div className="NewChallengeName">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton duration={2} />
                    </SkeletonTheme>
                </div>
                <div className="NewChallengeAuthor">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton duration={2} />
                    </SkeletonTheme>
                </div>
            </div>
        );
    }
    return Loading;
};
const RenderChallengeList = React.memo(props => {
    const data = props.data;
    const solved = props.solved;
    const GoChallenge = ChallengeID => {
        history.push(`/challenge/${ChallengeID}`);
    };

    const CheckSolved = id => {
        return solved.findIndex(item => item.id == id);
    };

    return data.map(item => {
        return (
            <div className="ChallengeCard" key={item.id}>
                <img
                    src={`/${item.challengeStage}.png`}
                    onClick={() => {
                        GoChallenge(item.id);
                    }}
                />
                <div
                    className="NewChallengeName"
                    onClick={() => {
                        GoChallenge(item.id);
                    }}
                >
                    <div className={CheckSolved(item.id) != -1 ? "SovledName" : "NotSolvedName"}>
                        {item.challengeName}
                    </div>
                    <div className="NewChallengeScore"> {item.challengeScore}</div>
                    <div className="NewChallengeAuthor"> {item.challengeAuthor}</div>
                </div>
            </div>
        );
    });
});

function ChallengeList(props) {
    const ChallengeRedux = useSelector(state => state.challenge);
    const SolvedRedux = useSelector(state => state.challenge.solved);

    ChallengeRedux.challenge.sort((a, b) => {
        return b.pid - a.pid;
    });

    const [data, setData] = useState(ChallengeRedux.challenge.slice(0, 10));
    const [startIndex, setStartIndex] = useState(0);
    const [endIndex, setEndIndex] = useState(1);
    const dispatch = useDispatch();

    useEffect(async () => {
        const ChallengeData = await get("/challenge");
        if (ChallengeData.data.success === true) {
            if (
                JSON.stringify(ChallengeRedux.challenge) !==
                JSON.stringify(ChallengeData.data.challengeList)
            ) {
                dispatch(UpdateChallenge(ChallengeData.data.challengeList));
                setData(ChallengeData.data.challengeList.slice(0, 10));
            }
        }
    }, []);

    const PlusIndex = () => {
        setData(ChallengeRedux.challenge.slice(10 * (startIndex + 1), 10 * (endIndex + 1)));
        setStartIndex(prevValue => prevValue + 1);
        setEndIndex(prevValue => prevValue + 1);
    };

    const MinusIndex = () => {
        setData(ChallengeRedux.challenge.slice(10 * (startIndex - 1), 10 * (endIndex - 1)));
        setStartIndex(prevValue => prevValue - 1);
        setEndIndex(prevValue => prevValue - 1);
    };

    if (ChallengeRedux.challenge.length < 1) {
        return (
            <div className="ChallengeListWrap">
                <div className="ChallengeText">최신 게임들을 확인해보세요!</div>
                <div className="ChallengeList">
                    <RenderChallengeLoadingList />
                    <div className="ChallengeListButtonWrap">
                        <button onClick={MinusIndex} disabled={startIndex === 0 ? true : false}>
                            &lt;
                        </button>
                        <button
                            onClick={PlusIndex}
                            disabled={
                                endIndex * 10 > ChallengeRedux.challenge.length ? true : false
                            }
                        >
                            &gt;
                        </button>
                    </div>
                </div>
            </div>
        );
    } else {
        return (
            <>
                <div className="ChallengeListWrap">
                    <div className="ChallengeText">최신 게임들을 확인해보세요!</div>
                    <div className="ChallengeList">
                        <RenderChallengeList data={data} solved={SolvedRedux} />
                    </div>
                </div>
                <div className="ChallengeListButtonWrap">
                    <button onClick={MinusIndex} disabled={startIndex === 0 ? true : false}>
                        &lt;
                    </button>
                    <button
                        onClick={PlusIndex}
                        disabled={endIndex * 10 >= ChallengeRedux.challenge.length ? true : false}
                    >
                        &gt;
                    </button>
                </div>
            </>
        );
    }
}

export default ChallengeList;
