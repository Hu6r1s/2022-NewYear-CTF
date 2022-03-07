import React, { useState, useEffect } from "react";
import "./Notice.css";
import Skeleton, { SkeletonTheme } from "react-loading-skeleton";
import { get } from "../../../utils/axios/axiosManage";
import parse from "html-react-parser";
import history from "../../../history/history"
export default function Notice(props) {
    const { noticeId } = props.match.params;
    const [Load, setLoad] = useState(false);
    const [NoticeInfo, setNoticeInfo] = useState();
    useEffect(async () => {
        const NoticeData = await get(`/notice/${noticeId}`);
        if (NoticeData.data.success === true) {
            setNoticeInfo(NoticeData.data.notice);
            setLoad(true);
        }
        else {
            history.push("/notice");
        }
    }, []);

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
    if (Load) {
        return (
            <div className="NoticeInfoWrap">
                <div className="NoticeInfoTitle">{NoticeInfo.noticeTitle}</div>
                <div className="NoticeInfoWriter">작성자: SecurityFirst</div>
                <div className="NoticeInfoCreatedAt">
                    작성 시간: {timeParse(NoticeInfo.createdAt)}
                </div>
                <div className="NoticeInfoContent">{parse(NoticeInfo.noticeContent)}</div>
            </div>
        );
    } else {
        return (
            <div className="NoticeInfoWrap">
                <div className="NoticeInfoTitle">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton />
                    </SkeletonTheme>
                </div>
                <div className="NoticeInfoWriter">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton />
                    </SkeletonTheme>
                </div>
                <div className="NoticeInfoCreatedAt">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton />
                    </SkeletonTheme>
                </div>
                <div className="NoticeInfoContent">
                    <SkeletonTheme color="#303030" highlightColor="#4b4b4b">
                        <Skeleton height="200px" />
                    </SkeletonTheme>
                </div>
            </div>
        );
    }
}
