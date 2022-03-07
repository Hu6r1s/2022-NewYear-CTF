import React, { useState, useEffect } from "react";
import "./MainContent.css";
import history from "../../../history/history";
import Skeleton, { SkeletonTheme } from "react-loading-skeleton";
import { get } from "../../../utils/axios/axiosManage";
import { useDispatch, useSelector } from "react-redux";
import { UpdateChallenge } from "../../../_actions/ChallengeAction";

const LoadingHotGameList = React.memo(() => {
  const Loading = [];
  for (let i = 0; i < 10; i++) {
      Loading.push(
          <div className="HotGameCard" key={i}>
              <div className="GameRanking">{i + 1}</div>
              <div className="HotGameImg">
                  <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                      <Skeleton duration={0.5} width="60px" height="60px" />
                  </SkeletonTheme>
              </div>
              <div className="HotGameTitle">
                  <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                      <Skeleton duration={0.5} />
                  </SkeletonTheme>
              </div>
              <div className="HotGameAuth">
                  <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                      <Skeleton duration={0.5} />
                  </SkeletonTheme>
              </div>
              <div className="HotGameCategoryAndScore">
                  <div className="HotGameCategory">
                      <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                          <Skeleton duration={0.5} />
                      </SkeletonTheme>
                  </div>
                  <div className="HotGameScore">
                      <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                          <Skeleton duration={0.5} />
                      </SkeletonTheme>
                  </div>
              </div>
          </div>
      );
  }
  return Loading;
});

const RenderHotGameList = props => {
  const HotGameList = props.HotGameData;
  const GoChallenge = ChallengeID => {
      history.push(`/challenge/${ChallengeID}`);
  };

  if (HotGameList.length >= 1) {
      return HotGameList.map((item, index) => {
          return (
              <div
                  className="HotGameCard"
                  onClick={() => {
                      GoChallenge(item.id);
                  }}
                  key={index}
              >
                  <div className="GameRanking">{index + 1}</div>
                  <div className="HotGameImg">
                      <img src={`/${item.challengeAuthor}.png`} alt="challengeAuth"></img>
                  </div>
                  <div className="HotGameTitle">{item.challengeName}</div>
                  <div className="HotGameAuth">{item.challengeAuthor}</div>
                  <div className="HotGameCategoryAndScore">
                      <div className="HotGameCategory">
                          {item.challengeCategory.toUpperCase()}
                      </div>
                      <div className="HotGameScore">{item.challengeScore}</div>
                  </div>
              </div>
          );
      });
  } else {
      return <LoadingHotGameList />;
  }
};

export default function MainContent() {
  const ChallengeRedux = useSelector(state => state.challenge);
    const GoCategoryInfo = category => {
        history.push(`/category/${category}`);
    };
    const [ChallengeTopTen, setChallengeTopTenData] = useState([]);
    const dispatch = useDispatch();
    useEffect(async () => {
        const ChallengeData = await get("/challenge");

        if (ChallengeData.data.success === true) {
            if (
                JSON.stringify(ChallengeRedux.challenge) !==
                JSON.stringify(ChallengeData.data.challengeList)
            ) {
                dispatch(UpdateChallenge(ChallengeData.data.challengeList));
            }
        }
        const ChallengeTopTenData = await get("/rank/challenge");
        setChallengeTopTenData(ChallengeTopTenData.data.challengeList);
    }, []);
    
  return (
    <div className="MainContentWrap">
      <div className="RecommandPlayList">
        <div className="RecommandText">회원님께 추천하는 게임</div>
        <div className="CardFlexBox">
          <div className="PlayListCard">
            <img
              src="/web.jpg"
              alt="web"
              onClick={() => {
                GoCategoryInfo("web");
              }}
              alt="web"
            ></img>
            <div
              className="RecommandCategroyText"
              onClick={() => {
                GoCategoryInfo("web");
              }}
            >
              WEB
            </div>
          </div>
          <div className="PlayListCard">
            <img
              src="/pwnable.jpg"
              alt="pwnable"
              onClick={() => {
                GoCategoryInfo("pwnable");
              }}
              alt="pwnable"
            ></img>
            <div
              className="RecommandCategroyText"
              onClick={() => {
                GoCategoryInfo("pwnable");
              }}
            >
              PWNALBE
            </div>
          </div>

          <div className="PlayListCard">
            <img
              src="/reversing.jpg"
              alt="reversing"
              onClick={() => {
                GoCategoryInfo("reversing");
              }}
              alt="reversing"
            ></img>
            <div
              className="RecommandCategroyText"
              onClick={() => {
                GoCategoryInfo("reversing");
              }}
            >
              REVERSING
            </div>
          </div>

          <div className="PlayListCard">
            <img
              src="/forensic.jpg"
              alt="forensic"
              onClick={() => {
                GoCategoryInfo("forensic");
              }}
              alt="forensic"
            ></img>
            <div
              className="RecommandCategroyText"
              onClick={() => {
                GoCategoryInfo("forensic");
              }}
            >
              FORENSIC
            </div>
          </div>

          <div className="PlayListCard">
            <img
              src="/misc.jpg"
              alt="misc"
              onClick={() => {
                GoCategoryInfo("misc");
              }}
              alt="misc"
            ></img>
            <div
              className="RecommandCategroyText"
              onClick={() => {
                GoCategoryInfo("misc");
              }}
            >
              MISC
            </div>
          </div>

          {/* <div className="PlayListCard">
            <img
              src="/event.jpg"
              alt="event"
              onClick={() => {
                GoCategoryInfo("event");
              }}
              alt="event"
            ></img>
            <div
              className="RecommandCategroyText"
              onClick={() => {
                GoCategoryInfo("event");
              }}
            >
              EVENT
            </div>
          </div> */}
        </div>
      </div>
      <div className="HotGameList">
        <div className="HotGameText">실시간 Hot한 게임</div>
        <div className="HotGameItemList">
          <RenderHotGameList HotGameData={ChallengeTopTen} />
        </div>
      </div>
      {/* <div className="NewMusicList">
        <div className="NewMusicText">최신 음악</div>
        <div className="PlayListCardWrap">
          <RenderNewGameList NewGameData={ChallengeRedux} />
        </div>
      </div> */}
    </div>
  );
}
