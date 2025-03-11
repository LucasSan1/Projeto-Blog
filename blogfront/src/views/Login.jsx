import React, { useState, useContext } from "react";
import { AuthContext } from "../contexts/auth";
import { Link } from "react-router-dom";
import { api } from "../services/api";

const LoginPage = () => {
    const { login } = useContext(AuthContext);

    const [usuario, setUsuario] = useState("");
    const [senha, setSenha] = useState("");

    const enviar = (e) => {
        e.preventDefault();

        const emailValidation = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

        if (usuario.trim() === "" || senha.trim() === "") {
            alert("Por favor, preencha todos os campos.");
        } else if (!emailValidation.test(usuario)) {
            alert("Por favor, insira um usuario válido.");
        } else {
            api.post('/user/login', {
                email: usuario,
                password: senha
            })
            .then((response) => {
                const token = response.data.token;
                login(usuario, token);
                console.log("Logado");
            })
            .catch((erro) => {
                console.log(erro);
            });
        }
    }

    return (
        <div className="flex flex-col items-center justify-center bg-[#F4F4F4] min-h-screen px-8">
            <div className="bg-white max-w-md w-full p-6 shadow-lg rounded-lg">
                <h1 className="text-2xl font-bold text-[#333333] text-center mb-6">Login</h1>
                <form onSubmit={enviar}>
                    <div className="mb-4">
                        <label htmlFor="email" className="block text-lg italic text-[#333333]">Usuário:</label>
                        <input
                            name="email"
                            type="text"
                            id="email"
                            value={usuario}
                            onChange={(e) => setUsuario(e.target.value)}
                            className="w-full h-7 border border-[#007BFF] rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-[#007BFF]"
                        />
                    </div>
                    <div className="mb-6">
                        <label htmlFor="senha" className="block text-lg italic text-[#333333]">Senha:</label>
                        <input
                            name="senha"
                            type="password"
                            id="senha"
                            value={senha}
                            onChange={(e) => setSenha(e.target.value)}
                            className="w-full h-7 border border-[#007BFF] rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-[#007BFF]"
                        />
                    </div>
                    <div className="flex justify-between items-center">
                        <button type="submit" className="bg-[#FF6F61] text-white text-lg py-2 px-6 rounded-lg hover:scale-105 transition-transform duration-200">Entrar</button>
                        <Link to="/cadastro" className="text-[#333333] text-sm mt-2 hover:text-[#FF6F61] hover:scale-105 transition-transform duration-200">Cadastrar-se</Link>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default LoginPage;
