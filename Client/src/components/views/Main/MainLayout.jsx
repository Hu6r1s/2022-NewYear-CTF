import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./MainLayout.css";
import { useSelector } from "react-redux";
import { Init } from "../../../_actions/UserAction";
import { useDispatch } from "react-redux";
import { SolvedList } from "../../../_actions/ChallengeAction";
import { LogoutUser } from "../../../_actions/UserAction";
import history from "../../../history/history";
import { ToastContainer } from "react-toastify";
import { get } from "../../../utils/axios/axiosManage";
import "react-toastify/dist/ReactToastify.css";

export default function MainLayout(props) {
    const { View, Menu } = props;
    const LoginRedux = useSelector(state => state);
    const PlayListData = useSelector(state => state.challenge.solved);
    const dispatch = useDispatch();
    useEffect(async () => {
        if (!LoginRedux.user) {
            dispatch(Init());
        }
        if (!LoginRedux.user.login) {
            dispatch(Init());
        }
        if (LoginRedux.user.login.success === true) {
            const PlayListResponse = await get(`/info`);
            dispatch(SolvedList(PlayListResponse.data.mySolveList));
        }
    }, []);

    const LoginState = LoginRedux.user.login.success;
    const LogoutHandler = () => {
        sessionStorage.removeItem("accessToken");
        sessionStorage.removeItem("refreshToken");
        sessionStorage.removeItem("uid");
        dispatch(LogoutUser());
        history.push("/");
    };

    return (
        <div className="MainWrap">
            <ToastContainer
                className="MyInfoToast"
                position="top-center"
                autoClose={2000}
                hideProgressBar={false}
                newestOnTop={false}
                pauseOnFocusLoss={false}
                closeOnClick
                rtl={false}
                draggable
                pauseOnHover={false}
            />
            <div className="MainTopWrap">
                <div className="MainLogo">
                    <img
                        src="/logo.png"
                        onClick={() => {
                            history.push("/");
                        }}
                    />
                </div>
                <div
                    className="MainTitle"
                    onClick={() => {
                        history.push("/");
                    }}
                >
                    SF Game
                </div>
            </div>
            <div className='MainViewWrap'>
                <div className="MainLeftWrap">
                    <div className="MenuList">
                        <div className="MenuItem">
                            <div className={Menu == "Main" ? "SeletedMenuButton" : "MenuButton"}>
                                <img
                                    src={Menu == "Main" ? "/selecthome.png" : "/home.png"}
                                />
                                <Link to="/">홈</Link>
                            </div>
                            <div className="MenuItem">
                                <div className={Menu == "Challenge" ? "SeletedMenuButton" : "MenuButton"}>
                                    <img
                                        src={Menu == "Challenge" ? "/selectmusic.png" : "/music.png"}
                                    />
                                    <Link to="/challenge">최신 게임</Link>
                                </div>
                            </div>
                            <div className="MenuItem">
                                <div className={Menu == "Category" ? "SeletedMenuButton" : "MenuButton"}>
                                    <img
                                        src={Menu == "Category" ? "/selectcategory.png" : "/category.png"}
                                    />
                                    <Link to="/category">게임 장르</Link>
                                </div>
                            </div>
                            <div className="MenuItem">
                                <div className={Menu == "Ranking" ? "SeletedMenuButton" : "MenuButton"}>
                                    <img
                                        src={Menu == "Ranking" ? "/selectranking.png" : "/ranking.png"}
                                    />
                                    <Link to="/ranking">랭킹</Link>
                                </div>
                            </div>
                            <div className="MenuItem">
                                <div className={Menu == "MyInfo" ? "SeletedMenuButton" : "MenuButton"}>
                                    <img
                                        src={Menu == "MyInfo" ? "/selectmyinfo.png" : "/myinfo.png"}
                                    />
                                    <Link to="/myinfo">내 정보</Link>
                                </div>
                            </div>
                        </div>

                        <div className="MenuBottom">
                            <div className="MenuItem">
                                <button className={Menu == "Notice" ? "SeletedMenuButton" : "MenuButton"}>
                                    <Link to="/notice">공지사항</Link>
                                </button>
                            </div>
                            <div className="MenuItem">
                                <button className={Menu == "Login" ? "SeletedMenuButton" : "MenuButton"}>
                                    {LoginState ? (
                                        <div className="LogoutText" onClick={LogoutHandler}>
                                            로그아웃
                                        </div>
                                    ) : (
                                        <Link to="/login">로그인</Link>
                                    )}
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="MainRightWrap" className="MainRightWrap">
                    <View {...props} />
                    {/* <div id="MainRightView" className="MainRightView">
                        <View {...props} />
                    </div> */}
                </div>
            </div>
        </div>
    );
}
