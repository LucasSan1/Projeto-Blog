import React from "react";
import { Link } from "react-router-dom";
import Header from "../components/Header";
import PostBox from "../components/PostBox";

const MainPage = () => {
    return (
        <>
            <Header />
            <div className="pt-24"> 
                <PostBox />
            </div>

            <Link 
                to="/criar-post"
                className="fixed bottom-4 right-4 bg-[#FF6F61] text-white text-2xl p-4 rounded-full shadow-lg hover:scale-105 transition-transform duration-200"
            >
                Novo post
            </Link>
        </>
    );
};

export default MainPage;
