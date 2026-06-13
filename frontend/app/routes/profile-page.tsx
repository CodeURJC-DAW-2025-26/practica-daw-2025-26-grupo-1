import { Link, useNavigate } from "react-router";
import { Container, Form, Button, Alert } from "react-bootstrap";
import { ArrowLeft, Check2 } from "react-bootstrap-icons";
import { useState } from "react";
import type { FormEvent } from "react";
import { updateMyProfile } from "~/services/user-service"; 
import { useUserStore } from "~/stores/user-store";

const getUserAvatarUrl = (user: any) => {
    if (user?.userImage?.id) {
        return `/api/v1/users/${user.id}/image`; 
    }
    return "/perfil-sin-foto.png";
};

export default function ProfilePage() {
    const navigate = useNavigate();
    const [isPending, setIsPending] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const { user, loadLoggedUser } = useUserStore();

    const [previewImage, setPreviewImage] = useState<string>(getUserAvatarUrl(user));
    const [username, setUsername] = useState(user?.name || "");
    
    const [selectedFile, setSelectedFile] = useState<File | null>(null);

    function handleImageChange(event: React.ChangeEvent<HTMLInputElement>) {
        const file = event.target.files?.[0];
        if (file) {
            setSelectedFile(file); 
            const reader = new FileReader();
            reader.onloadend = () => {
                setPreviewImage(reader.result as string); 
            };
            reader.readAsDataURL(file);
        }
    }

    function handleUsernameChange(event: React.ChangeEvent<HTMLInputElement>) {
        setUsername(event.target.value);
    }

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        if (!user) return;
        
        setIsPending(true);
        setError(null); 

        const formData = new FormData(event.currentTarget);
        const password = formData.get("password") as string;

        try {
            await updateMyProfile(user.id, username, false, selectedFile);
            
            await loadLoggedUser();
            
            const messageSuccess = "Perfil actualizado correctamente.";
            navigate(`/notification?type=confirmation&message=${encodeURIComponent(messageSuccess)}`);
            
        } catch (err) {
            setError("Error al actualizar el perfil del usuario.");
        } finally {
            setIsPending(false);
        }
    }

    return (
        <div className="second-hero d-flex align-items-center py-5">
            <Container className="px-4 px-lg-5 my-5">
                <div className="row gx-4 gx-lg-5 align-items-center justify-content-center">
                    <div className="col-md-6">
                        <div className="card mb-4 rounded-3 shadow-sm border-primary">
                            <div className="modal-content rounded-4 shadow">
                                <div className="modal-header p-5 pb-4 border-bottom-0 justify-content-center">
                                    <h1 className="fw-bold mb-0 fs-2">Su perfil</h1>
                                </div>
                                <div className="modal-body p-5 pt-0">
                                    
                                    <Form onSubmit={handleSubmit} className="mb-4">
                                        
                                        <div className="d-flex justify-content-center mb-3">
                                            <img 
                                                src={previewImage} 
                                                alt="Avatar de perfil" 
                                                className="rounded-circle" 
                                                style={{ height: '80px', width: '80px', objectFit: 'cover' }} 
                                                onError={(e) => {
                                                    (e.target as HTMLImageElement).src = "/perfil-sin-foto.png";
                                                }}
                                            />
                                        </div>

                                        <Form.Group className="mb-3">
                                            <Form.Label htmlFor="imageField" className="fw-medium">Cambiar imagen:</Form.Label>
                                            <Form.Control 
                                                type="file" 
                                                name="imageField" 
                                                id="imageField" 
                                                accept=".png" 
                                                onChange={handleImageChange} 
                                                disabled={isPending} 
                                            />
                                        </Form.Group>

                                        <Form.Floating className="mb-3">
                                            <Form.Control 
                                                type="text" 
                                                placeholder="Nombre de usuario" 
                                                name="name" 
                                                id="name"
                                                required 
                                                value={username}            
                                                onChange={handleUsernameChange} 
                                                disabled={isPending} 
                                            />
                                            <label htmlFor="name">Nombre de usuario</label>
                                        </Form.Floating>

                                        <Form.Floating className="mb-4">
                                            <Form.Control 
                                                type="password" 
                                                placeholder="Confirmar contraseña para guardar" 
                                                name="password" 
                                                id="password"
                                                required 
                                                disabled={isPending} 
                                            />
                                            <label htmlFor="password">Contraseña actual o nueva</label>
                                        </Form.Floating>

                                        {error && (
                                            <Alert variant="danger" className="text-center py-2 mb-3">
                                                {error}
                                            </Alert>
                                        )}

                                        <Button 
                                            variant="success" 
                                            type="submit" 
                                            className="w-100 d-flex align-items-center justify-content-center py-2 fs-5" 
                                            disabled={isPending}
                                        >
                                            <Check2 className="me-2" />
                                            {isPending ? "Guardando..." : "Aceptar"}
                                        </Button>
                                    </Form>

                                    <small className="text-body-secondary text-center d-block">
                                        Al hacer clic en "Aceptar", aceptas las condiciones de uso.
                                    </small>
                                </div>
                            </div>
                        </div>

                        <div className="d-flex justify-content-center gap-3">
                            <Link to="/sections" className="btn btn-danger px-4 py-2 fs-5 d-flex align-items-center">
                                <ArrowLeft className="me-2" />
                                Volver
                            </Link>
                        </div>

                    </div>
                </div>
            </Container>
        </div>
    );
}