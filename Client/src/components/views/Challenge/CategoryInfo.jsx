import React, { useState, useEffect } from "react";
import "./CategoryInfo.css";
import history from "../../../history/history";
import Skeleton, { SkeletonTheme } from "react-loading-skeleton";
import { get } from "../../../utils/axios/axiosManage";
import { useDispatch, useSelector } from "react-redux";
import { UpdateChallenge } from "../../../_actions/ChallengeAction";

const CategoryIntro = {
    web: "당신도 웹 서비스를 해킹할 수 있습니다.",
    pwnable: "신형 pwn 중고 pwn 비싸게 사고 싸게 팝니다.",
    reversing: "분석 좀 해줄래? 이 앨범 좀 듣게",
    forensic: "컴퓨터 안에 증거가 있잖아!!!",
    misc: "힘들거나 지칠땐 misc",
    event: "게임 속 숨겨진 이벤트"
};

const CategoryArtist = {
    web: "Value, HU6R1S, k0dePenguin",
    pwnable: "OZ1NG",
    reversing: "Ch1o3",
    forensic: "FurD3n",
    misc: "HU6R1S, OZ1NG, Value, k0dePenguin, Ch1o3",
    event: "SecurityFirst"
};

const LoadCategoryChallengeList = () => {
    const Loading = [];
    for (let i = 0; i < 10; i++) {
        Loading.push(
            <div className="CategoryMusicCard" key={i}>
                <div className="CategoryMusicIndex">{i + 1}</div>
                <div className="CategoryMusicImg">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton duration={0.7} width={"60px"} height={"60px"} />
                    </SkeletonTheme>
                </div>
                <div className="CategoryMusicSong">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton duration={0.7} />
                    </SkeletonTheme>
                </div>
                <div className="CategoryMusicSinger">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton duration={0.7} />
                    </SkeletonTheme>
                </div>
                <div className="CategoryMusicAndScore">
                    <div className="CategoryMusicCategory">
                        <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                            <Skeleton duration={0.7} />
                        </SkeletonTheme>
                    </div>
                    <div className="CategoryMusicScore">
                        <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                            <Skeleton duration={0.7} />
                        </SkeletonTheme>
                    </div>
                </div>
            </div>
        );
    }
    return Loading;
};

// const CategoryInfoLoad = () => {
//     return (
//         <div className="CategoryInfoWrap">
//             <div className="CategoryInfomation">
//                 <div>
//                     <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
//                         <Skeleton duration={0.7} width={"220px"} height={"250px"} />
//                     </SkeletonTheme>
//                 </div>
//                 <div className="CategoryInfoTextWrap">
//                     <div className="CategoryInfoText">
//                         <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
//                             <Skeleton duration={0.7} width={"100%px"} />
//                         </SkeletonTheme>
//                     </div>
//                     <div className="CategoryInfo">
//                         <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
//                             <Skeleton duration={0.7} width={"100%px"} />
//                         </SkeletonTheme>
//                     </div>
//                     <div className="CategoryInfo">
//                         <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
//                             <Skeleton duration={0.7} width={"100%px"} />
//                         </SkeletonTheme>
//                     </div>
//                     <div className="CategoryInfo">
//                         <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
//                             <Skeleton duration={0.7} width={"100%px"} />
//                         </SkeletonTheme>
//                     </div>
//                     <div className="CategoryInfo">
//                         <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
//                             <Skeleton duration={0.7} width={"100%px"} />
//                         </SkeletonTheme>
//                     </div>
//                     <div className="CategoryInfo">
//                         <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
//                             <Skeleton duration={0.7} width={"100%px"} />
//                         </SkeletonTheme>
//                     </div>
//                 </div>
//             </div>
//             <div className="CategoryMusicList">
//                 <LoadCategoryChallengeList />
//             </div>
//         </div>
//     );
// };

const CategoryChallengeList = props => {
    const GoChallenge = ChallengeID => {
        history.push(`/challenge/${ChallengeID}`);
    };
    const CheckSolved = id => {
        return solved.findIndex(item => item.id == id);
    };
    const ChallengeData = props.ChallengeData;
    const solved = props.solved;
    return ChallengeData.map((item, index) => {
        return (
            <div className="CategoryMusicCard" key={item.id}>
                <img
                    src={`/${item.challengeStage}.png`}
                    onClick={() => {
                        GoChallenge(item.id);
                    }}
                />
                <div className="CategoryMusicName"
                    onClick={() => {
                        GoChallenge(item.id);
                    }}
                >

                    <div className="CategoryMusicIndex">{index + 1}</div>
                    <div className={CheckSolved(item.id) != -1 ? "SovledName" : "NotSolvedName"}>
                        {item.challengeName}
                    </div>
                    <div className="CategoryMusicScore">{item.challengeScore}</div>
                    <div className="CategoryMusicAuthor">{item.challengeAuthor}</div>
                </div>
            </div>
        );
    });
};
function CategoryInfo(props) {
    const [Load, setLoad] = useState(false);
    const [CategoryChallenge, setCategoryChallenge] = useState([]);
    const { category } = props.match.params;
    const ChallengeRedux = useSelector(state => state.challenge);
    const SolvedRedux = useSelector(state => state.challenge.solved);

    ChallengeRedux.challenge.sort((a, b) => {
        return b.pid - a.pid;
    });

    const [startIndex, setStartIndex] = useState(0);
    const [endIndex, setEndIndex] = useState(1);
    const [data, setData] = useState(ChallengeRedux.challenge.slice(0, 10));
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
    const returnAllScore = data => {
        let score = 0;
        data.map(item => (score += item.challengeScore));
        return score;
    };

    useEffect(async () => {
        const challData = await get(`/challenge/category/${category}`);
        if (challData.data.success === true) {
            setCategoryChallenge(challData.data.challengeList);
            setLoad(true);
        }
    }, []);
    if (Load) {
        return (
            <div className="CategoryInfoWrap">
                <div className="CategoryInfomation">
                    <img src={`/${category}.jpg`}></img>
                    <div className="CategoryInfoTextWrap">
                        <div className="CategoryInfoText"> {category.toUpperCase()}</div>
                        <div className="CategoryInfo">
                            관련 아티스트: {CategoryArtist[category]}
                        </div>
                        <div className="CategoryInfo">
                            총 클리어 점수: {returnAllScore(CategoryChallenge)}
                        </div>
                        <div className="CategoryInfo">게임 수 : {CategoryChallenge.length}개</div>
                        <div className="CategoryInfo">한줄 소개: {CategoryIntro[category]}</div>
                    </div>
                </div>
                <div className="CategoryMusicList">
                    <CategoryChallengeList ChallengeData={CategoryChallenge} solved={SolvedRedux} />
                </div>
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
        );
    } else {
        return <></>;
    }
}

export default CategoryInfo;
