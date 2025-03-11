import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../contexts/auth";

const Header = () => {
    const { authenticated, logout } = useContext(AuthContext);

    const handleLoginLogout = () => {
        if (authenticated) {
            logout(); 
        } else {
           
            window.location.href = "/login";
        }
    };

    return (
        <div className="fixed top-0 left-0 w-full bg-[#F4F4F4] p-4 shadow-md z-50">
            <div className="flex items-center justify-between">
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
                    <button 
                        onClick={handleLoginLogout}
                        className="bg-[#FF6F61] text-white text-lg py-2 px-6 rounded-lg hover:scale-105 transition-transform duration-200"
                    >
                        {authenticated ? "Logout" : "Login"} {/* Alterna entre Login e Logout */}
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Header;
