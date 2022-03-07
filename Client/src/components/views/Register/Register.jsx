import React, { useState, useRef } from "react";
import TextField from "@material-ui/core/TextField";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import "./Register.css";
import axios from "axios";
import history from "../../../history/history";
import { toast } from "react-toastify";

export default function Register() {
    const [userIdErrorMessage, setUserIdErrorMessage] = useState("");
    const [userIdError, setUserIdError] = useState(false);

    const [passwordErrorMessage, setPasswordErrorMessage] = useState("");
    const [passwordError, setPasswordError] = useState(false);

    const [passwordCheckErrorMessage, setPasswordCheckErrorMessage] = useState("");
    const [passwordCheckError, setPasswordCheckError] = useState(false);

    const [nickErrorMessage, setNickErrorMessage] = useState("");
    const [nickError, setNickError] = useState(false);

    const [teamErrorMessage, setTeamErrorMessage] = useState("");
    const [teamError, setTeamError] = useState(false);

    const [team, setTeam] = useState("null");

    const userId = useRef("");
    const password = useRef("");
    const passwordCheck = useRef("");
    const nick = useRef("");

    const teamChange = e => {
        setTeam(e.target.value);

        if (e.target.value === "null") {
            setTeamError(true);
            setTeamErrorMessage("소속을 선택해주세요.");
        } else {
            setTeamError(false);
            setTeamErrorMessage("");
        }
    };

    const userIdValidator = async () => {
        const IDRoll = /^[a-z0-9_]{4,20}$/;
        if (userId.current.value === "") {
            setUserIdError(true);
            setUserIdErrorMessage("아이디를 입력해주세요.");
            return false;
        } else if (!IDRoll.test(userId.current.value)) {
            setUserIdError(true);
            setUserIdErrorMessage(
                "아이디는 알파벳 소문자와 숫자를 사용하여 4 ~ 20 글자여야합니다."
            );
            return false;
        } else if (await checkDuplicateUserId()) {
            setUserIdError(true);
            setUserIdErrorMessage("이미 사용중인 아이디입니다.");
            return false;
        }
        setUserIdError(false);
        setUserIdErrorMessage("");
        return true;
    };

    const passwordValidator = () => {
        const PassWordRoll = /^[\w\W]{8,20}$/;
        if (password.current.value === "") {
            setPasswordError(true);
            setPasswordErrorMessage("비밀번호를 입력해주세요.");
            return false;
        } else if (!PassWordRoll.test(password.current.value)) {
            setPasswordError(true);
            setPasswordErrorMessage("비밀번호는 8 ~ 20 글자여야합니다.");
            return false;
        }
        setPasswordError(false);
        setPasswordErrorMessage("");
        return true;
    };

    const passwordCheckValidator = () => {
        if (password.current.value !== passwordCheck.current.value) {
            setPasswordCheckError(true);
            setPasswordCheckErrorMessage("비밀번호가 일치하지 않습니다.");
            return false;
        }
        setPasswordCheckError(false);
        setPasswordCheckErrorMessage("");
        return true;
    };

    const nickValidator = () => {
        if (nick.current.value === "") {
            setNickError(true);
            setNickErrorMessage("입력해주세요.");
            return false;
        }
        setNickError(false);
        setNickErrorMessage("");
        return true;
    };

    const teamValidator = () => {
        if (team === "null") {
            setTeamError(true);
            setTeamErrorMessage("소속을 선택해주세요.");
            return false;
        }
        setTeamError(false);
        setTeamErrorMessage("");
        return true;
    };

    const checkDuplicateUserId = async () => {
        const response = await axios.get(
            `/api/register/id/${userId.current.value}`
        );
        return response.data.result;
    };

    const registerRequest = async () => {
        const userIdValidate = await userIdValidator();
        const passwordValidate = passwordValidator();
        const passwordCheckValidate = passwordCheckValidator();
        const nickValidate = nickValidator();
        const teamValidate = teamValidator();

        if (
            !userIdValidate ||
            !passwordValidate ||
            !passwordCheckValidate ||
            !nickValidate ||
            !teamValidate
        ) {
            return;
        }

        const data = {
            userId: userId.current.value,
            password: password.current.value,
            nick: nick.current.value,
            team
        };

        const registerResponse = await axios.post("/api/register", data);

        if (registerResponse.data.success) {
            toast.dark("회원가입에 성공하였습니다.");
            history.push("/login");
        } else {
            toast.dark("회원가입을 실패했습니다.");
        }
    };

    return (
        <div className="RegisterFormWrap">
            <div className="FormWrap">
                <TextField
                    placeholder="아이디"
                    fullWidth
                    variant="outlined"
                    inputRef={userId}
                    onBlur={userIdValidator}
                />
                {userIdError ? <div className="ErrorMessage">{userIdErrorMessage}</div> : <></>}
            </div>
            <div className="FormWrap">
                <TextField
                    placeholder="비밀번호"
                    fullWidth
                    type="password"
                    variant="outlined"
                    inputRef={password}
                    onBlur={passwordValidator}
                />
                {passwordError ? <div className="ErrorMessage">{passwordErrorMessage}</div> : <></>}
            </div>
            <div className="FormWrap">
                <TextField
                    placeholder="비밀번호 확인"
                    fullWidth
                    type="password"
                    variant="outlined"
                    inputRef={passwordCheck}
                    onBlur={passwordCheckValidator}
                />
                {passwordCheckError ? (
                    <div className="ErrorMessage">{passwordCheckErrorMessage}</div>
                ) : (
                    <></>
                )}
            </div>
            <div className="FormWrap">
                <TextField
                    placeholder="학번_이름 ex) 18_정현준"
                    fullWidth
                    variant="outlined"
                    inputRef={nick}
                    onBlur={nickValidator}
                />
                {nickError ? <div className="ErrorMessage">{nickErrorMessage}</div> : <></>}
            </div>
            <div className="FormWrap">
                <Select
                    value={team}
                    onChange={teamChange}
                    style={{ width: 500, textAlign: "center" }}
                >
                    <MenuItem value={"null"}>소속 선택</MenuItem>
                    <MenuItem value={"SecurityFirst_NB"}>Security NB</MenuItem>
                    <MenuItem value={"SecurityFirst_YB"}>Security YB</MenuItem>
                    <MenuItem value={"SecurityFirst_OB"}>Security OB</MenuItem>
                </Select>
                {teamError ? <div className="ErrorMessage">{teamErrorMessage}</div> : <></>}
            </div>
            <div className="RegisterButtonWrap">
                <button className="RegisterBtn" onClick={registerRequest}>
                    회원가입
                </button>
            </div>
        </div>
    );
}
