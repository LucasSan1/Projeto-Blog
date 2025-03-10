import React from "react";

import Header from "../components/Header";
import PostBox from "../components/PostBox";


const MainPage = () => {

    return (
        <>
        <Header />
        
        <div className="p-4">
                <PostBox />
        </div>

        </>
    )
}


export default MainPage;