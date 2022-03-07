import React, { useState } from "react";
import "./ChallengeCategory.css";
import history from "../../../history/history";
function ChallengeCategory() {
    const GoCategoryInfo = category => {
        history.push(`/category/${category}`);
    };

    return (
        <div className="ChallengeCategoryWrap">
            <div className="ChallengeCategoryText">게임들을 클리어해보세요!</div>
            <div className="ChallengeCategoryCardList">
                <div className="ChallengeCategoryCard">
                    <img
                        src="/web.jpg"
                        onClick={() => {
                            GoCategoryInfo("web");
                        }}
                    />
                    <div
                        className="CategoryName"
                        onClick={() => {
                            GoCategoryInfo("web");
                        }}
                    >
                        WEB
                    </div>
                </div>
                <div className="ChallengeCategoryCard">
                    <img
                        src="/pwnable.jpg"
                        onClick={() => {
                            GoCategoryInfo("pwnable");
                        }}
                    />
                    <div
                        className="CategoryName"
                        onClick={() => {
                            GoCategoryInfo("pwnable");
                        }}
                    >
                        PWNABLE
                    </div>
                </div>
                <div className="ChallengeCategoryCard">
                    <img
                        src="/reversing.jpg"
                        onClick={() => {
                            GoCategoryInfo("reversing");
                        }}
                    />
                    <div
                        className="CategoryName"
                        onClick={() => {
                            GoCategoryInfo("reversing");
                        }}
                    >
                        REVERSING
                    </div>
                </div>
                <div className="ChallengeCategoryCard">
                    <img
                        src="/forensic.jpg"
                        onClick={() => {
                            GoCategoryInfo("forensic");
                        }}
                    />
                    <div
                        className="CategoryName"
                        onClick={() => {
                            GoCategoryInfo("forensic");
                        }}
                    >
                        FORENSIC
                    </div>
                </div>
                <div className="ChallengeCategoryCard">
                    <img
                        src="/misc.jpg"
                        onClick={() => {
                            GoCategoryInfo("misc");
                        }}
                    />
                    <div
                        className="CategoryName"
                        onClick={() => {
                            GoCategoryInfo("misc");
                        }}
                    >
                        MISC
                    </div>
                </div>
                <div className="ChallengeCategoryCard">
                    <img
                        src="/event.jpg"
                        onClick={() => {
                            GoCategoryInfo("event");
                        }}
                    />
                    <div
                        className="CategoryName"
                        onClick={() => {
                            GoCategoryInfo("event");
                        }}
                    >
                        EVENT
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ChallengeCategory;
