import React from "react";
import { Link } from "react-router-dom";

const Header = () => {
    return (
        <div className="flex items-center justify-between bg-[#F4F4F4] p-4 shadow-md">
            <div className="text-[#333333] text-3xl font-bold">
                <Link to="/">Blog</Link>
            </div>

            <div className="flex justify-center flex-1 mx-8">
                <input 
                    type="text"
                    placeholder="Buscar no blog..."
                    className="w-full max-w-lg p-2 border border-[#007BFF] rounded-lg focus:outline-none focus:ring-2 focus:ring-[#007BFF]"
                />
            </div>

            <div>
                <Link 
                    to="/login" 
                    className="bg-[#FF6F61] text-white text-lg py-2 px-6 rounded-lg hover:scale-105 transition-transform duration-200"
                >
                    Login
                </Link>
            </div>
        </div>
    );
};

export default Header;
