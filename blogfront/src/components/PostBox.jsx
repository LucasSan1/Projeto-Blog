import React, { useState, useEffect } from "react";
import { api } from "../services/api";
import { formatDistanceToNow, parseISO } from 'date-fns';
import { ptBR } from 'date-fns/locale'; 

// Função para formatar a data nas postagens e comentarios
const formatRelativeTime = (dateString) => {
    if (!dateString) return "Data não disponível"; 
    
    try {
        const date = parseISO(dateString); 
        const distance = formatDistanceToNow(date, { addSuffix: true, locale: ptBR }); 
        return distance; 
    } catch (error) {
        return "Data inválida"; 
    }
};

const PostBox = () => {
    const [posts, setPosts] = useState([]);
    const [newComment, setNewComment] = useState(""); 

    useEffect(() => {
        api.get("/posts")
            .then((res) => {
                setPosts(res.data.reverse());
            })
            .catch((err) => alert("Deu erro => " + err));
    }, []);

    const handleNewComment = (e) => {
        e.preventDefault();
        alert("Comentário enviado: " + newComment);
        setNewComment(""); 
    };

    if (!posts || posts.length === 0) {
        return <div>Carregando...</div>;
    }

    return (
        <div className="space-y-6">
            {posts.map((item, index) => (
                <div key={index} className="bg-gray-50 p-6 rounded-lg shadow-md border border-gray-300 mb-6">

                    <div className="flex justify-between text-sm text-gray-500 mb-4">
                        <span className="font-semibold">{item.authorName}</span>
                        <span>{formatRelativeTime(item.dateTime) || "Data não disponível"}</span>
                    </div>

                    {/* Título da Postagem */}
                    <h1 className="text-2xl font-bold text-[#333333] mb-4">{item.title}</h1>

                    <div className="mb-4 text-gray-700">
                        <p>{item.content}</p>
                    </div>

                    {/* Seção de Comentários */}
                    <div className="bg-gray-100 p-4 rounded-lg mt-6 border-t-2 border-gray-300">
                        <h2 className="text-lg font-semibold text-[#333333] mb-4">Comentários</h2>
                        <div className="space-y-4">
                            {item.comments && item.comments.length > 0 ? (
                                item.comments.map((comment, index) => (
                                    <div key={index} className="border-b border-gray-300 pb-4">
                                        <div className="flex justify-between items-center">
                                            <span className="font-semibold text-gray-800">{comment.author_name}</span>
                                            <span className="text-sm text-gray-500">{formatRelativeTime(comment.datetime) || "Data não disponível"}</span>
                                        </div>
                                        <p className="text-gray-600 mt-2">{comment.content}</p>
                                    </div>
                                ))
                            ) : (
                                <p className="text-gray-600">Ainda não há comentários.</p>
                            )}

                            {/* Input para novo comentário*/}
                            <form onSubmit={handleNewComment} className="mt-4 flex">
                                <input
                                    type="text"
                                    className="w-full p-2 border border-gray-300 rounded-lg"
                                    placeholder="Digite seu comentário..."
                                    value={newComment}
                                    onChange={(e) => setNewComment(e.target.value)}
                                />
                                <button
                                    type="submit"
                                    className="bg-[#007BFF] text-white px-4 py-2 rounded-lg ml-2"
                                >
                                    Enviar
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default PostBox;
