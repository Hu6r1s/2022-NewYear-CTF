import React, { useState, useEffect } from "react";
// import ReactApexChart from "react-apexcharts";
import "./Ranking.css";
import history from "../../../history/history";
import { get } from "../../../utils/axios/axiosManage";
import CircularProgress from "@material-ui/core/CircularProgress";

const goUserInfo = nick => {
    history.push(`/user/${nick}`);
}

const RenderChallengeRanking = props => {
    const rankingData = props.rankingData;
    const Move = target => {
        history.push(`/challenge/${target}`);
    };
    return (
        <div className="RankingWrap">
            <div className="ChallengeRankingWrap">
                {/* <div className="ChallengeRankingText">실시간 음원 랭킹</div> */}
                {/* <ReactApexChart
                    options={charts.options}
                    series={charts.series}
                    type="line"
                    height={350}
                /> */}
            </div>
            <div className="ChallengeRankingListWrap">
                {rankingData.map((item, index) => {
                    return (
                        <div
                            className="ChallengeRankingCard"
                            onClick={() => {
                                Move(item.id);
                            }}
                        >
                            <div className="MusicRanking">{index + 1}</div>
                            <div className="ChallengeRankingImg">
                                <img src={`/${item.challengeAuthor}.png`} />
                            </div>
                            <div className="ChallengeRankingSong">{item.challengeName}</div>
                            <div className="ChallengeRankingSinger">{item.challengeAuthor}</div>
                            <div className="ChallengeRankingCategoryAndScore">
                                <div className="ChallengeRankingCategory">
                                    {item.challengeCategory}
                                </div>
                                <div className="ChallengeRankingScore">
                                    {item.challengeScore} 점
                                </div>
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
};

const SolverRankingList = props => {
    const rankingData = props.rankingData;
    const timeParse = timedata => {
        let time = new Date(timedata);
        let year = time.getFullYear().toString(); //년도 뒤에 두자리
        let month = ("0" + (time.getMonth() + 1)).slice(-2); //월 2자리 (01, 02 ... 12)
        let day = ("0" + time.getDate()).slice(-2); //일 2자리 (01, 02 ... 31)
        let hour = ("0" + time.getHours()).slice(-2); //시 2자리 (00, 01 ... 23)
        let minute = ("0" + time.getMinutes()).slice(-2); //분 2자리 (00, 01 ... 59)
        let second = ("0" + time.getSeconds()).slice(-2); //초 2자리 (00, 01 ... 59)
        let returnDate = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        return returnDate;
    };
    return (
        <div className="SolverRankingListWrap">
            {rankingData.map((item, index) => {
                if (item.user !== null) {
                    return (
                        <div className="ChallengeRankingCard">
                            <div className="MusicRanking">{index + 1}</div>
                            <div
                                className="ArtistRankingSinger"
                                onClick={() => {
                                    goUserInfo(item.nick);
                                }}
                            >
                                {item.nick}
                            </div>
                            <div className="ArtistRankingScore">{item.scoreSum}</div>
                            <div className="ArtistRankingTeam">{item.team}</div>
                            <div className="ArtistRankingLastSolveTime">
                                마지막 풀이시간 : {timeParse(item.lastSolveTime)}
                            </div>
                        </div>
                    );
                } else {
                    return <></>;
                }
            })}
        </div>
    );
};

const RenderArtistRanking = props => {
    // const threeData = props.threeData;
    const rankingData = props.rankingData;
    const { selectType, setSelectType } = props;
    // const returnTime = minus => {
    //     let time = new Date();
    //     let hour = time.getHours();
    //     hour = hour - minus;
    //     if (hour <= 0) {
    //         hour += 24;
    //     }
    //     return hour;
    // };

    // const ParseScore = (data, index) => {
    //     const score = [];
    //     for (let i = 8; i >= 1; i--) {
    //         score.push(data[i - 1][index].score);
    //     }
    //     return score;
    // };
    // const [charts, setCharts] = useState({
    //     series: [
    //         {
    //             name: tenData[0].user.nickname,
    //             data: ParseScore(threeData, 0)
    //         },
    //         {
    //             name: tenData[1].user.nickname,
    //             data: ParseScore(threeData, 1)
    //         },
    //         {
    //             name: tenData[2].user.nickname,
    //             data: ParseScore(threeData, 2)
    //         }
    //     ],
    //     options: {
    //         chart: {
    //             height: 350,
    //             type: "line",
    //             zoom: {
    //                 enabled: false
    //             },
    //             foreColor: "#FFFFFF",
    //             toolbar: {
    //                 show: false
    //             }
    //         },
    //         dataLabels: {
    //             enabled: false,
    //             style: {
    //                 colors: ["#FFFFFF"]
    //             }
    //         },
    //         stroke: {
    //             curve: "straight"
    //         },

    //         grid: {
    //             row: {
    //                 colors: ["#495057", "transparent"] // takes an array which will be repeated on columns
    //             }
    //         },
    //         tooltip: {
    //             enabled: false
    //         },
    //         xaxis: {
    //             categories: [
    //                 returnTime(7) + "시",
    //                 returnTime(6) + "시",
    //                 returnTime(5) + "시",
    //                 returnTime(4) + "시",
    //                 returnTime(3) + "시",
    //                 returnTime(2) + "시",
    //                 returnTime(1) + "시",
    //                 returnTime(0) + "시"
    //             ]
    //         }
    //     }
    // });

    return (
        <div className="RankingWrap">
            <div className="ChallengeRankingWrap">
                {/* <div className="ChallengeRankingText">실시간 유저 랭킹</div> */}
                {/* <ReactApexChart
                    options={charts.options}
                    series={charts.series}
                    type="line"
                    height={350}
                /> */}
            </div>
            <div className="ChallengeRankingListWrap">
                <div className="SolverRankingTypeButtonWrap">
                    <div
                        className={
                            selectType === "all"
                                ? "SelectedSolverTypeButton"
                                : "NotSelectedSolverTypeButton"
                        }
                        onClick={() => {
                            if (selectType !== "all") {
                                setSelectType("all");
                            }
                        }}
                    >
                        전체 랭킹
                    </div>
                    <div
                        className={
                            selectType === "yb"
                                ? "SelectedSolverTypeButton"
                                : "NotSelectedSolverTypeButton"
                        }
                        onClick={() => {
                            if (selectType !== "yb") {
                                setSelectType("yb");
                            }
                        }}
                    >
                        YB 랭킹
                    </div>
                    <div
                        className={
                            selectType === "ob"
                                ? "SelectedSolverTypeButton"
                                : "NotSelectedSolverTypeButton"
                        }
                        onClick={() => {
                            if (selectType !== "ob") {
                                setSelectType("ob");
                            }
                        }}
                    >
                        OB 랭킹
                    </div>
                    <div
                        className={
                            selectType === "nb"
                                ? "SelectedSolverTypeButton"
                                : "NotSelectedSolverTypeButton"
                        }
                        onClick={() => {
                            if (selectType !== "nb") {
                                setSelectType("nb");
                            }
                        }}
                    >
                        NB 랭킹
                    </div>
                </div>
                <SolverRankingList rankingData={props.rankingData} />
            </div>
        </div>
    );
};

export default function Ranking() {
    const [currentSelect, setCurrentSelect] = useState("user");
    const [Load, setLoad] = useState(false);
    // const [threeData, setThreeData] = useState([]);
    const [rankingData, setRankingData] = useState([]);
    const [selectType, setSelectType] = useState("all");
    const SelectChange = value => {
        if (currentSelect !== value) {
            setCurrentSelect(value);
            setLoad(prevValue => !prevValue);
        }
    };
    useEffect(async () => {
        if (currentSelect === "user") {
            // const threeDataResult = await get(`/ranking/${currentSelect}/top3`);
            const rankingData = await get(`/rank/solver/${selectType}`);
            if (rankingData.data.success) {
                setRankingData(rankingData.data.solverRank);
                // setThreeData(threeDataResult.data);
                setLoad(true);
            }
        } else {
            // const threeDataResult = await get(`/ranking/${currentSelect}/top3`);
            const rankingData = await get(`/rank/challenge`);
            if (rankingData.data.success) {
                setRankingData(rankingData.data.challengeList);
                // setThreeData(threeDataResult.data);
                setLoad(true);
            }
        }
    }, [currentSelect]);

    useEffect(async () => {
        const rankingData = await get(`/rank/solver/${selectType}`);
        if (rankingData.data.success) {
            setRankingData(rankingData.data.solverRank);
            // setThreeData(threeDataResult.data);
            setLoad(true);
        }
    }, [selectType]);
    if (Load) {
        return (
            <>
                <div className="SelectRankingContent">
                    <div
                        className={
                            currentSelect === "user"
                                ? "SelectedRankingButton"
                                : "SelectRankingButton"
                        }
                        onClick={() => SelectChange("user")}
                    >
                        유저 랭킹
                    </div>
                    <div
                        className={
                            currentSelect === "prob"
                                ? "SelectedRankingButton"
                                : "SelectRankingButton"
                        }
                        onClick={() => SelectChange("prob")}
                    >
                        게임 랭킹
                    </div>
                </div>
                {currentSelect === "prob" ? (
                    <RenderChallengeRanking rankingData={rankingData}/*  threeData={threeData}  *//>
                ) : (
                    <RenderArtistRanking
                        rankingData={rankingData}
                        // threeData={threeData}
                        selectType={selectType}
                        setSelectType={setSelectType}
                    />
                )}
            </>
        );
    } else {
        return (
            <>
                <div className="LoadingRankingWrap">
                    {/* <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton circle={true} duration={0.5} width={"100%"} height={"100vh"} />
                    </SkeletonTheme> */}
                    <CircularProgress color="secondary" />
                </div>
            </>
        );
    }
}
