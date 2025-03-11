import React, { useState } from "react";
import Header from "../components/Header";
import PostBox from "../components/PostBox";
import NovoPost from "../components/ModalPost";

const MainPage = () => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    
    const handleClose = () => {
        setIsModalOpen(false);

    };

    return (
        <>
            <Header />
            <div className="pt-24"> 
                <PostBox />
            </div>

            <button 
                className="fixed bottom-4 right-4 bg-[#FF6F61] text-white text-2xl p-4 rounded-full shadow-lg hover:scale-105 transition-transform duration-200"
                onClick={() => setIsModalOpen(true)}
            >
                Novo post
            </button>

            <NovoPost 
                isOpen={isModalOpen} 
                onClose={() => setIsModalOpen(false)} 
                onCreate={handleClose} 

            />
        </>
    );
};

export default MainPage;