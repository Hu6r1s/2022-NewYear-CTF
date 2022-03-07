import React, { useState, useEffect } from "react";
import "./NoticeList.css";
import history from "../../../history/history";
import Skeleton, { SkeletonTheme } from "react-loading-skeleton";
import { get } from "../../../utils/axios/axiosManage";
const LoadingNoticeList = () => {
    const Loading = [];
    for (let i = 0; i < 6; i++) {
        Loading.push(
            <div className="NoticeListItem" key={i}>
                <div className="NoticeID">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton />
                    </SkeletonTheme>
                </div>
                <div className="NoticeTitle">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton />
                    </SkeletonTheme>
                </div>
                <div className="NoticeWriter">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton />
                    </SkeletonTheme>
                </div>
                <div className="NoticeCreatedAt">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton />
                    </SkeletonTheme>
                </div>
            </div>
        );
    }
    return Loading;
};
const RenderNoticeList = props => {
    const Move = target => {
        history.push(`/notice/${target}`);
    };
    const { data } = props;
    return data.map((item, index) => {
        return (
            <div
                className="NoticeListItem"
                onClick={() => {
                    Move(item.id);
                }}
            >
                <div className="NoticeID">{index + 1}</div>
                <div className="NoticeTitle">{item.noticeTitle}</div>
                <div className="NoticeWriter">SecurityFirst</div>
            </div>
        );
    });
};
export default function NoticeList() {
    const [Load, setLoad] = useState(false);
    const [NoticeData, setNoticeData] = useState();
    useEffect(async () => {
        const NoticeListData = await get("/notice");
        if (NoticeListData.data.success === true) {
            setNoticeData(NoticeListData.data.noticeList.reverse());
            setLoad(true);
        }
    }, []);
    if (Load) {
        return (
            <div className="NoticeWrap">
                <div className="NoticeText">SF Game 공지사항</div>
                <div className="NoticeListWrap">
                    <RenderNoticeList data={NoticeData} />
                </div>
            </div>
        );
    } else {
        return (
            <div className="NoticeWrap">
                <div className="NoticeText">SF Game 공지사항</div>
                <div className="NoticeListWrap">
                    <LoadingNoticeList />
                </div>
            </div>
        );
    }
}
