import React, { useState, useEffect } from "react";
import { api } from "../services/api";
import { formatDistanceToNow, parseISO } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import { FiEdit, FiCheck, FiX, FiTrash2 } from "react-icons/fi";
import ModalConfirmar from "./ModalConfirmar";

const formatRelativeTime = (dateString) => {
    if (!dateString) return "Data não disponível";
    try {
        const date = parseISO(dateString);
        return formatDistanceToNow(date, { addSuffix: true, locale: ptBR });
    } catch (error) {
        return "Data inválida";
    }
};

const PostBox = () => {
    const [posts, setPosts] = useState([]);
    const [newComment, setNewComment] = useState("");
    const [editingPost, setEditingPost] = useState(null);
    const [editingComment, setEditingComment] = useState(null);
    const [editedContent, setEditedContent] = useState("");
    const [editedTitle, setEditedTitle] = useState("");
    const [editedCategory, setEditedCategory] = useState("");
    const [isModalOpen, setIsModalOpen] = useState(false); 
    const [itemToDelete, setItemToDelete] = useState(null); 
    const [itemType, setItemType] = useState('');  

    useEffect(() => {
        api.get("/posts")
            .then((res) => setPosts(res.data.reverse()))
            .catch((err) => alert("Deu erro => " + err));
    }, []);

    const handleNewComment = (e, postID) => {
        e.preventDefault();
        api.post(`/comments/${postID}`, { content: newComment }, {
            headers: { Authorization: localStorage.getItem("Authorization") }
        })
        .then(() => {
            window.location.reload();
        })
        .catch((err) => {
            alert(err.response?.status === 401 ? "Você precisa estar logado para comentar!" : "Erro ao comentar!");
        });
        setNewComment("");
    };

    const handleEditPost = (post) => {
        setEditingPost(post.id);
        setEditedContent(post.content);
        setEditedTitle(post.title);
        setEditedCategory(post.category);
    };

    const savePostEdit = (post) => {
        if (editedTitle === post.title && editedContent === post.content && editedCategory === post.category) {
            alert("Nenhuma mudança foi feita.");
            setEditingPost(null);
            return;
        }

        api.put(`/posts/${post.id}`, {
            title: editedTitle || post.title,
            content: editedContent || post.content,
            category: editedCategory || post.category,
        }, {
            headers: { Authorization: localStorage.getItem("Authorization") }
        }).then(() => window.location.reload())
          .catch(() => alert("Erro ao editar post"));
    };

    const deletePost = () => {
        if (itemToDelete) {
            api.delete(`/posts/${itemToDelete.id}`, {
                headers: { Authorization: localStorage.getItem("Authorization") }
            }).then(() => {
                setIsModalOpen(false);
                window.location.reload();
            }).catch(() => alert("Erro ao excluir post"));
        }
    };

    const deleteComment = () => {
        if (itemToDelete) {
            api.delete(`/comments/${itemToDelete.id}`, {
                headers: { Authorization: localStorage.getItem("Authorization") }
            }).then(() => {
                setIsModalOpen(false);
                window.location.reload();
            }).catch(() => alert("Erro ao excluir comentário"));
        }
    };

    const handleModalClose = () => {
        setIsModalOpen(false);
        setItemToDelete(null); 
        setItemType('');  
    };

    const handleConfirmDelete = () => {
        if (itemType === 'post') {
            deletePost();
        } else if (itemType === 'comment') {
            deleteComment();
        }
    };

    return (
        <div className="space-y-6">
            {posts.map((post) => (
                <div key={post.id} className="bg-gray-50 p-6 rounded-lg shadow-md border border-gray-300 mb-6">
                    <div className="flex justify-between text-sm text-gray-500 mb-4">
                        <div className="flex items-center space-x-2">
                            <span className="font-semibold">{post.authorName}</span>
                            <span className="text-gray-500">| Categoria: {post.category}</span>
                        </div>
                        <div className="flex items-center space-x-2">
                            <span>{formatRelativeTime(post.dateTime)}</span>
                            <button onClick={() => handleEditPost(post)} className="text-blue-500 text-sm">
                                <FiEdit />
                            </button>
                            <button onClick={() => {
                                setItemToDelete(post);
                                setItemType('post'); 
                                setIsModalOpen(true);  
                            }} className="text-red-500 text-sm flex items-center">
                                <FiTrash2 /> Excluir
                            </button>
                        </div>
                    </div>

                    <h1 className="text-2xl font-bold text-[#333333] mb-4">
                        {editingPost === post.id ? (
                            <input
                                type="text"
                                value={editedTitle}
                                onChange={(e) => setEditedTitle(e.target.value)}
                                className="w-full p-2 border rounded-lg"
                            />
                        ) : (
                            post.title
                        )}
                    </h1>

                    {editingPost === post.id ? (
                        <div>
                            <textarea value={editedContent} onChange={(e) => setEditedContent(e.target.value)} className="w-full p-2 border rounded-lg"></textarea>
                            <div className="mt-4">
                                <input
                                    type="text"
                                    value={editedCategory}
                                    onChange={(e) => setEditedCategory(e.target.value)}
                                    className="w-full p-2 border rounded-lg"
                                    placeholder="Categoria"
                                />
                            </div>
                            <button onClick={() => savePostEdit(post)} className="text-green-500 mx-2"><FiCheck /></button>
                            <button onClick={() => setEditingPost(null)} className="text-red-500"><FiX /></button>
                        </div>
                    ) : (
                        <p className="mb-4 text-gray-700">{post.content}</p>
                    )}

                    <div className="bg-gray-100 p-4 rounded-lg mt-6 border-t-2 border-gray-300">
                        <h2 className="text-lg font-semibold text-[#333333] mb-4">Comentários</h2>
                        <div className="space-y-4">
                            {post.comments && post.comments.length > 0 ? (
                                post.comments.map((comment) => (
                                    <div key={comment.id} className="border-b border-gray-300 pb-4">
                                        <div className="flex justify-between items-center">
                                            <span className="font-semibold text-gray-800">{comment.author_name}</span>
                                            <div className="flex items-center space-x-2">
                                                <span className="text-sm text-gray-500">{formatRelativeTime(comment.datetime)}</span>
                                                <button onClick={() => {
                                                    setItemToDelete(comment);
                                                    setItemType('comment'); 
                                                    setIsModalOpen(true);  
                                                }} className="text-red-500 text-sm">
                                                    <FiTrash2 /> Excluir
                                                </button>
                                            </div>
                                        </div>
                                        <p className="text-gray-600 mt-2">{comment.content}</p>
                                    </div>
                                ))
                            ) : (
                                <p className="text-gray-600">Ainda não há comentários.</p>
                            )}
                        </div>
                    </div>
                </div>
            ))}

            {/* Modal de confirmação de exclusão */}
            <ModalConfirmar 
                isOpen={isModalOpen}
                onClose={handleModalClose}
                onConfirm={handleConfirmDelete}
                message={itemType === 'post' ? "Você tem certeza que deseja excluir este post?" : "Você tem certeza que deseja excluir este comentário?"}
            />
        </div>
    );
};

export default PostBox;
