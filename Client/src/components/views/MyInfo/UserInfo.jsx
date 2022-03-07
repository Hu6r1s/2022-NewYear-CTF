import React, { useState, useEffect } from "react";
import "./MyInfo.css";
import history from "../../../history/history";
import Skeleton, { SkeletonTheme } from "react-loading-skeleton";
import { get } from "../../../utils/axios/axiosManage";

const RenderChallengeList = React.memo(props => {
    const GoChallenge = ChallengeID => {
        history.push(`/challenge/${ChallengeID}`);
    };

    const data = props.data;
    return data.map((item, index) => {
        return (
            <div
                className="MyInfoChallengeCard"
                onClick={() => {
                    GoChallenge(item.id);
                }}
                key={item.id}
            >
                <div className="MyInfoChallengeRanking">{index + 1}</div>
                <div className="MyInfoChallengeImg">
                    <img src={`/${item.challengeAuthor}.png`} alt="MyInfoChallengeAuth" />
                </div>
                <div className="MyInfoChallengeName">{item.challengeName}</div>
                <div className="MyInfoChallengeAuthor">{item.challengeAuthor}</div>
                <div className="MyInfoChallengeCategoryAndScore">
                    <div className="MyInfoChallengeCategory">
                        {item.challengeCategory.toUpperCase()}
                    </div>
                    <div className="MyInfoChallengeScore">{item.challengeScore}</div>
                </div>
            </div>
        );
    });
});

const LoadChallengeList = () => {
    const Loading = [];
    for (let i = 0; i < 12; i++) {
        Loading.push(
            <div className="MyInfoChallengeCard" key={i}>
                <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                    <Skeleton width="100%" height="150px" />
                </SkeletonTheme>
                <div className="MyInfoChallengeName">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton />
                    </SkeletonTheme>
                </div>
                <div className="MyInfoChallengeAuthor">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton />
                    </SkeletonTheme>
                </div>
            </div>
        );
    }
    return Loading;
};

export default function MyInfo(props) {
    const [allSolvedList, setAllSolvedList] = useState([]);
    const [data, setData] = useState([]);
    const [startIndex, setStartIndex] = useState(0);
    const [endIndex, setEndIndex] = useState(1);
    const [Load, setLoad] = useState(false);
    const [userInfo, setUserInfo] = useState();
    const nick = props.match.params.nick;

    useEffect(async () => {
        try {
            const myInfo = await get(`/info/${nick}`);
            if (myInfo.data.success === true) {
                setAllSolvedList(myInfo.data.mySolveList);
                setData(myInfo.data.mySolveList.slice(0, 12));
                setUserInfo({ nick: myInfo.data.nick, score: myInfo.data.myScore });
                setLoad(true);
            }
            else {
                history.push("/");
            }
        }
        catch {
            history.push("/");
        }
    }, []);

    const PlusIndex = () => {
        setData(allSolvedList.slice(12 * (startIndex + 1), 12 * (endIndex + 1)));
        setStartIndex(prevValue => prevValue + 1);
        setEndIndex(prevValue => prevValue + 1);
    };

    const MinusIndex = () => {
        setData(allSolvedList.slice(12 * (startIndex - 1), 12 * (endIndex - 1)));
        setStartIndex(prevValue => prevValue - 1);
        setEndIndex(prevValue => prevValue - 1);
    };

    if (Load) {
        return (
            <div className="MyInfoWrap">
                <div className="MyInfoNickWrap">
                    <div className="MyInfoNickText">닉네임</div>
                    <div className="MyInfoNick">{userInfo.nick}</div>
                </div>
                <div className="MyInfoScoreWrap">
                    <div className="MyInfoScoreText">총 클리어 점수</div>
                    <div className="MyInfoScore">{userInfo.score}</div>
                </div>
                <div className="MyInfoSolveListWrap">
                    <div className="MyInfoSolveListText">클리어 리스트</div>
                    <div className="MyInfoSolveList">
                        <RenderChallengeList data={data} />
                    </div>
                    <div className="MyInfoButtonWrap">
                        <button onClick={MinusIndex} disabled={startIndex === 0 ? true : false}>
                            &lt;
                        </button>
                        <button
                            onClick={PlusIndex}
                            disabled={endIndex * 12 >= allSolvedList.length ? true : false}
                        >
                            &gt;
                        </button>
                    </div>
                </div>
            </div>
        );
    } else {
        return (
            <div className="MyInfoWrap">
                <div className="MyInfoNickWrap">
                    <div className="MyInfoNickText">닉네임</div>
                    <div className="MyInfoNick">
                        <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                            <Skeleton width="200px" />
                        </SkeletonTheme>
                    </div>
                </div>
                <div className="MyInfoScoreWrap">
                    <div className="MyInfoScoreText">총 클리어 점수</div>
                    <div className="MyInfoScore">
                        <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                            <Skeleton width="200px" />
                        </SkeletonTheme>
                    </div>
                </div>
                <div className="MyInfoSolveListWrap">
                    <div className="MyInfoSolveListText">내 클리어 리스트</div>
                    <div className="MyInfoSolveList">
                        <LoadChallengeList />
                    </div>
                </div>
            </div>
        );
    }
}
