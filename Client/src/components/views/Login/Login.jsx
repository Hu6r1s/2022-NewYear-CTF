import { TextField } from '@mui/material';
import React, { useState, useRef } from 'react';
import "./Login.css";
import axios from "axios";
import { LoginUser } from "../../../_actions/UserAction";
import { useDispatch, useSelector } from "react-redux";
import { SolvedList } from "../../../_actions/ChallengeAction";
import history from "../../../history/history";
import { login, get } from "../../../utils/axios/axiosManage";

export default function Login(props) {
    const ID = useRef("");
    const Password = useRef("");
    const [ErrorMsg, setErrorMsg] = useState("");
    const dispatch = useDispatch();
    const SubmitLogin = async e => {
        e.preventDefault();
        if (ID.current.value == "" || Password.current.value == "") {
            setErrorMsg("아이디 또는 비밀번호를 입력해주세요.");
        } else {
            setErrorMsg("");
            const LoginData = {
                userId: ID.current.value,
                password: Password.current.value
            };

            const loginResult = await login(LoginData);

            if (loginResult.data.success) {
                const dispatchData = {
                    success: true
                };
                sessionStorage.setItem("accessToken", loginResult.data.accessToken);
                sessionStorage.setItem("refreshToken", loginResult.data.refreshToken);

                const PlayListResponse = await get("/info");

                dispatch(SolvedList(PlayListResponse.data.mySolveList));
                dispatch(LoginUser(dispatchData));
                history.push("/");
            } else {
                setErrorMsg("아이디 또는 비밀번호가 일치하지 않습니다.");
            }
        }
    };

    const goRegister = e => {
        history.push("/register");
    };
    return (
        <div className="LoginWrap">
            <div className="LoginLogo">
                <img src="/logo.png" alt="SFLogo" />
                <div className="LoginLogoText">SF Game</div>
            </div>
            <div className="LoginText">SF Game에 오신 것을 환영합니다.</div>
            <form onSubmit={SubmitLogin}>
                <div className="LoginFormWrap">
                    <div className="FormWrap">
                        <TextField 
                            placeholder="아이디"    
                            fullWidth
                            variant="outlined"
                            inputRef={ID}
                        />
                    </div>
                    <div className="FormWrap">
                        <TextField 
                            placeholder="비밀번호"
                            type="password"  
                            fullWidth
                            variant="outlined"
                            inputRef={Password}
                        />
                    </div>
                    <div className="LoginHelpText">{ErrorMsg}</div>
                    <button className="LoginBtn" type="submit">
                        로그인
                    </button>
                    <div className="RegisterText" onClick={goRegister}>
                        회원가입
                    </div>
                </div>
            </form>
        </div>
    );
};