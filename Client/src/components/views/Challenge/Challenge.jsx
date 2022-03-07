import React, { useState, useEffect, useRef } from "react";
import "./Challenge.css";
import TextField from "@material-ui/core/TextField";
import { useDispatch, useSelector } from "react-redux";
import Skeleton, { SkeletonTheme } from "react-loading-skeleton";
import parse from "html-react-parser";
import { toast } from "react-toastify";
import { get, post, fileDown } from "../../../utils/axios/axiosManage";
import { SolveChallenge, SolvedList } from "../../../_actions/ChallengeAction";
import history from "../../../history/history";
import FileDownload from "js-file-download";

const goUserInfo = (nick) => {
    history.push(`/user/${nick}`);
}

const ChallengeComment = props => {
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
    const CommentData = props.data;
    const ParseComment = () => {
        if (CommentData.length >= 1) {
            return CommentData.map(item => {
                return (
                    <div className="ChallengeCommentListItem" key={item.id}>
                        <div className="ChallengeCommentWriter" onClick={() => {
                            goUserInfo(item.nick);
                        }}>{item.nick}</div>
                        <div className="ChallengeCommentContent">{item.comment}</div>
                        <div className="ChallengeCommentCreatedAt">{timeParse(item.createdAt)}</div>
                    </div>
                );
            });
        } else {
            return (
                <div className="ChallengeCommentListItem">
                    <div className="NoComment">등록 된 리뷰이 없습니다.</div>
                </div>
            );
        }
    };
    return (
        <div className="ChallengeCommentList">
            <ParseComment />
        </div>
    );
};

const ChallengeCommentLoading = () => {
    const Loading = [];
    for (let i = 0; i < 6; i++) {
        Loading.push(
            <div className="ChallengeCommentList" key={i}>
                <div className="ChallengeCommentListItem">
                    <div className="ChallengeCommentWriter">
                        <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                            <Skeleton />
                        </SkeletonTheme>
                    </div>
                    <div className="ChallengeCommentContent">
                        <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                            <Skeleton />
                        </SkeletonTheme>
                    </div>
                    <div className="ChallengeCommentCreatedAt">
                        <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                            <Skeleton />
                        </SkeletonTheme>
                    </div>
                </div>
            </div>
        );
    }
    return Loading;
};

function Challenge(props) {
    const id = parseInt(props.match.params.id);
    const [ChallengeInfo, setChallengeInfo] = useState({});
    const [CommentData, setCommentData] = useState();
    const [Solved, setSolved] = useState(false);
    const [Load, setLoad] = useState(false);
    const [WaitFlag, setWaitFlag] = useState(false);
    const Flag = useRef("");
    const dispatch = useDispatch();
    const SolvedRedux = useSelector(state => state.challenge.solved);
    useEffect(async () => {
        document.querySelector("#MainRightWrap").scrollTo({ top: 0, left: 0, behavior: "smooth" });
        const ChallengeData = await get(`/challenge/${id}`);
        const CommentData = await get(`/challenge/comment/${id}`);
        if (ChallengeData.data.success === true) {
            setCommentData(CommentData.data.challengeCommentList);
            setChallengeInfo(ChallengeData.data);
            setSolved(ChallengeData.data.solved);
            setLoad(true);
        } else {
            history.push("/");
        }
    }, [id]);

    const SendFlag = async () => {
        setWaitFlag(true);
        setTimeout(() => {
            setWaitFlag(false);
        }, [1000]);

        if (Flag.current.value === "") {
            toast.error("플래그를 입력 해주세요.");
        } else if (WaitFlag) {
            toast.error("플래그는 1초마다 제출이 가능합니다.");
        } else {
            const data = {
                flag: Flag.current.value
            };
            const result = await post(`/challenge/${id}`, data);
            if (result.data.success === true) {
                const ChallengeData = await get(`/challenge/${id}`);
                const CommentData = await get(`/challenge/comment/${id}`);
                if (ChallengeData.data.success === true) {
                    setCommentData(CommentData.data.challengeCommentList);
                    setChallengeInfo(ChallengeData.data);
                    setSolved(ChallengeData.data.solved);
                    setLoad(true);
                }
                dispatch(
                    SolveChallenge({
                        id,
                        challengeName: ChallengeInfo.challengeName,
                        challengeAuthor: ChallengeInfo.challengeAuthor,
                        challengeCategory: ChallengeInfo.challengeCategory,
                        challengeScore: ChallengeInfo.challengeScore,
                        challengeStage: ChallengeInfo.challengeStage
                    })
                );
                setSolved(true);
                toast.dark("라운드 클리어에 성공하였습니다.");
            } else {
                toast.error("라운드 클리어가 맞는지 확인해주세요.");
            }
        }
    };
    const DownLoadBtn = async () => {
        if (ChallengeInfo.existChallengeFile === false) {
            toast.error("파일이 없는 문제입니다.");
        } else {
            const fileData = await fileDown(`/challenge/file/${id}`);
            FileDownload(
                fileData.data,
                fileData.headers["content-disposition"]
                    .split(";")[1]
                    .replace("filename=", "")
                    .replaceAll('"', "")
                    .replace(" ", "")
            );
        }
    };
    if (Load) {
        return (
            <div className="ChallengeWrap">
                <div className="ChallengeInfoName">{ChallengeInfo.challengeName}</div>
                <div className="ChallengeInfo">
                    <img
                        className="ChallengeImg"
                        src={`/${ChallengeInfo.challengeAuthor}.png`}
                    ></img>
                    <div className="ChallengeContent">
                        <div className="ChallengeInfoAuthor">
                            <div className="InfoText">디렉터</div>
                            <div className="InfoValue">{ChallengeInfo.challengeAuthor}</div>
                        </div>
                        <div className="ChallengeInfoWatchCount">
                            <div className="InfoText">조회 수</div>
                            <div className="InfoValue">{ChallengeInfo.challengeViews + 1}</div>
                        </div>
                        <div className="ChallengeInfoPlayCount">
                            <div className="InfoText">누적 클리어 수</div>
                            <div className="InfoValue">{ChallengeInfo.challengeSolverCount}</div>
                        </div>
                        <div className="ChallengeInfoScore">
                            <div className="InfoText">클리어 점수</div>
                            <div className="InfoValue">{ChallengeInfo.challengeScore}</div>
                        </div>
                    </div>
                </div>
                <div className="ChallengeFlagInput">
                    <div className="ChallengeFlagInputText">클리어 조건</div>
                    {Solved === false ? (
                        <div className="ChallengeFlagInputField">
                            <TextField
                                placeholder="플래그를 입력 해주세요."
                                fullWidth
                                variant="outlined"
                                inputRef={Flag}
                                style={{ marginRight: 30 }}
                            />
                            <div className="ChallengeCouponButton" onClick={SendFlag}>
                                클리어 하기
                            </div>
                        </div>
                    ) : (
                        <div className="ChallengeFlagInputField">
                            <div className="SolveText">이미 클리어 된 게임입니다.</div>
                        </div>
                    )}
                </div>
                <div className="ChallengeDownloadWrap">
                    <div className="ChallengeDownloadText">게임 다운로드</div>
                    <div className="ChallengeDownload" onClick={DownLoadBtn}>
                        다운로드
                    </div>
                </div>
                <div className="ChallengeStory">
                    <div className="ChallengeAuthorStory">
                        <div className="AuthorStoryText">게임 설명</div>
                        <div className="AuthorStory">
                            {parse(ChallengeInfo.challengeDescription)}
                        </div>
                    </div>
                </div>
                {<div className="ChallengeComment">
                    <div className="ChallengeCommentText">리뷰</div>
                    <ChallengeComment data={CommentData} />
                </div>}
            </div>
        );
    } else {
        return (
            <div className="ChallengeWrap">
                <div className="ChallengeInfoName">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton width="200px" />
                    </SkeletonTheme>
                </div>
                <div className="ChallengeInfo">
                    <img className="ChallengeImg"></img>
                    <div className="ChallengeContent">
                        <div className="ChallengeInfoAuthor">
                            <div className="InfoText">아티스트</div>
                            <div className="InfoValue">
                                <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                                    <Skeleton />
                                </SkeletonTheme>
                            </div>
                        </div>
                        <div className="ChallengeInfoWatchCount">
                            <div className="InfoText">조회 수</div>
                            <div className="InfoValue">
                                <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                                    <Skeleton />
                                </SkeletonTheme>
                            </div>
                        </div>
                        <div className="ChallengeInfoPlayCount">
                            <div className="InfoText">누적 클리어 수</div>
                            <div className="InfoValue">
                                <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                                    <Skeleton />
                                </SkeletonTheme>
                            </div>
                        </div>
                        <div className="ChallengeInfoScore">
                            <div className="InfoText">클리어 점수</div>
                            <div className="InfoValue">
                                <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                                    <Skeleton />
                                </SkeletonTheme>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="ChallengeFlagInput">
                    <div className="ChallengeFlagInputText">클리어 조건</div>
                    <div className="ChallengeFlagInputField">
                        <div className="ChallengeInputLoading">
                            <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                                <Skeleton height="40px" />
                            </SkeletonTheme>
                        </div>
                    </div>
                </div>
                <div className="ChallengeDownloadWrap">
                    <div className="ChallengeDownloadText">게임 다운로드</div>
                    <div className="ChallengeDownload">다운로드</div>
                </div>
                <div className="ChallengeStory">
                    <div className="ChallengeAuthorStory">
                        <div className="AuthorStoryText">게임 설명</div>
                        <div className="AuthorStory">
                            <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                                <Skeleton width="50%" height={"10vh"} />
                            </SkeletonTheme>
                        </div>
                    </div>
                </div>
                {<div className="ChallengeComment">
                    <div className="ChallengeCommentText">리뷰</div>
                    <ChallengeCommentLoading />
                </div>}
            </div>
        );
    }
}

export default Challenge;
