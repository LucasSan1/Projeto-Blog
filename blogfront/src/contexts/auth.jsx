import React, { useState, createContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";

// Define o contexto
export const AuthContext = createContext();


// Define o contexto que faz a validação dos dados
export const AuthProvider = ({ children }) => {
    const Navigate = useNavigate();
    const [user, setUser] = useState(null);
    const [carregando, setCarregando] = useState(true);

    // Função que recebe o usuario e o token da API e salva esses dados 
    const login = (usuario, token) => {
        if(usuario === undefined || token === undefined){
            alert("Não autenticado")
        }
        else{
            localStorage.setItem('user', JSON.stringify(usuario));
            localStorage.setItem('token', token);
            console.log("Logado")
            Navigate("/")
        }
    };

    // Função para logout do usuario e limpeza dos dados no localStorage
    const logout = () => {
        console.log("logout");
        localStorage.removeItem("user");
        localStorage.removeItem("token");
        setUser(null);
        Navigate("/");
    };

    // Antes da página ser renderizada, verifica se o usúario ainda esta logado no localStorage  
    useEffect(() => {
        const usuarioRecuperado = localStorage.getItem("user");
        const tokenRecuperado = localStorage.getItem("token");

        if (usuarioRecuperado && tokenRecuperado) {
            setUser(JSON.parse(usuarioRecuperado));
        }
        setCarregando(false);
    }, []);

    // Exporta os valores/variaveis para todo o sistema
    return (
        < AuthContext.Provider value={{ authenticated: !!user, user, carregando, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}