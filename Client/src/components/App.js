import React, { useState, useEffect } from "react";
import { Router, Route, Switch } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { Init } from "../_actions/UserAction";
import MainLayout from "./views/Main/MainLayout";
import history from "../history/history";
import Main from "./views/Main/MainContent";
import ChallengeList from "./views/Challenge/ChallengeList";
import Login from "./views/Login/Login";
import Challenge from "./views/Challenge/Challenge";
import ChallengeCategory from "./views/Challenge/ChallengeCategory";
import CategoryInfo from "./views/Challenge/CategoryInfo";
import Ranking from "./views/Ranking/Ranking";
import MyInfo from "./views/MyInfo/MyInfo";
import Notice from "./views/Notice/Notice";
import NoticeList from "./views/Notice/NoticeList";
import Register from "./views/Register/Register";
import UserInfo from "./views/MyInfo/UserInfo";

function App() {
  const LoginRedux = useSelector((state) => state);
  const dispatch = useDispatch();
  useEffect(() => {
    if (!LoginRedux.user) {
      dispatch(Init());
    }
    if (!LoginRedux.user.login) {
      dispatch(Init());
    }
  }, []);
  const LoginState = LoginRedux.user.login.success;
  return (
    <Router history={history}>
      <Switch>
        <Route
          exact
          path="/"
          render={(props) => {
            if (LoginState === true) {
              return <MainLayout {...props} View={Main} Menu={"Main"} />;
            } else {
              return <MainLayout {...props} View={Login} Menu={"Login"} />;
            }
          }}
        />
        <Route
          exact
          path="/challenge"
          render={(props) => {
            if (LoginState === true)
              return <MainLayout {...props} View={ChallengeList} Menu={"Challenge"} />;
            else {
              return <MainLayout {...props} View={Login} Menu={"Login"} />;
            }
          }}
        />
        <Route
          exact
          path="/login"
          render={(props) => {
            if (LoginState === true) {
              return <MainLayout {...props} View={Login} Menu={"Login"} />;
            } else {
              return <MainLayout {...props} View={Login} Menu={"Login"} />;
            }
          }}
        />
        <Route
          exact
          path="/register"
          render={(props) => {
            if (LoginState === true) {
              return (
                <MainLayout {...props} View={Register} Menu={"Register"} />
              );
            } else {
              return (
                <MainLayout {...props} View={Register} Menu={"Register"} />
              );
            }
          }}
        />
        <Route
          exact
          path="/challenge/:id"
          render={props => {
            if (LoginState === true) {
              return <MainLayout {...props} View={Challenge} Menu={"Challenge"} />;
            } else {
              return <MainLayout {...props} View={Login} Menu={"Login"} />;
            }
          }}
        />
        <Route
          exact
          path="/category/:category"
          render={props => {
            if (LoginState === true) {
              if (
                props.match.params.category === "web" ||
                props.match.params.category === "pwnable" ||
                props.match.params.category === "reversing" ||
                props.match.params.category === "forensic" ||
                props.match.params.category === "misc" ||
                props.match.params.category === "event"
              ) {
                return (
                  <MainLayout {...props} View={CategoryInfo} Menu={"Category"} />
                );
              } else {
                return <MainLayout {...props} View={Main} Menu={"Main"} />;
              }
            } else {
              return <MainLayout {...props} View={Login} Menu={"Login"} />;
            }
          }}
        />
        <Route
          exact
          path="/category"
          render={props => {
            if (LoginState === true) {
              return (
                <MainLayout {...props} View={ChallengeCategory} Menu={"Category"} />
              );
            } else {
              return <MainLayout {...props} View={Login} Menu={"Login"} />;
            }
          }}
        />
        <Route
          exact
          path="/ranking"
          render={props => {
            if (LoginState === true) {
              return <MainLayout {...props} View={Ranking} Menu={"Ranking"} />;
            } else {
              return <MainLayout {...props} View={Login} Menu={"Login"} />;
            }
          }}
        />
        <Route
          exact
          path="/myinfo"
          render={props => {
            if (LoginState === true) {
              return <MainLayout {...props} View={MyInfo} Menu={"MyInfo"} />;
            } else {
              return <MainLayout {...props} View={Login} Menu={"Login"} />;
            }
          }}
        />
        <Route
          exact
          path="/user/:nick"
          render={props => {
            if (LoginState === true) {
              return <MainLayout {...props} View={UserInfo} Menu={"MyInfo"} />;
            } else {
              return <MainLayout {...props} View={Login} Menu={"Login"} />;
            }
          }}
        />
        <Route
          exact
          path="/notice"
          render={props => {
            if (LoginState === true) {
                return <MainLayout {...props} View={NoticeList} Menu={"Notice"} />;
            } else {
                return <MainLayout {...props} View={Login} Menu={"Login"} />;
            }
          }}
        />
        <Route
          exact
          path="/notice/:noticeId"
          render={props => {
            if (LoginState === true) {
              return <MainLayout {...props} View={Notice} Menu={"Notice"} />;
            } else {
              return <MainLayout {...props} View={Login} Menu={"Login"} />;
            }
          }}
        />
        <Route
          exact
          path="*"
          render={props => <MainLayout {...props} View={Main} Menu={"Main"} />}
        />
      </Switch>
    </Router>
  );
}

export default App;
