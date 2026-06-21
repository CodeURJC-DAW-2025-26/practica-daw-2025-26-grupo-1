import { Link, useNavigate } from "react-router";
import { Container, Form, Button, Alert } from "react-bootstrap";
import { ArrowLeft, Check2 } from "react-bootstrap-icons";
import { useState} from "react";
import type { FormEvent } from "react";
import { register } from "~/services/user-service"; 
import { useUserStore } from "~/stores/user-store";

export default function RegisterPage() {
    const navigate = useNavigate();
    const [isPending, setIsPending] = useState(false);
    
    const [error, setError] = useState<string | null>(null);

    const userStore = useUserStore(); 

async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setIsPending(true);
    setError(null); 

    const formData = new FormData(event.currentTarget);
    const username = formData.get("name") as string;
    const password = formData.get("password") as string;

    try {
        await register(username, password);
        
        await userStore.loginUser(username, password);

        const messageSuccess = "Usuario registrado correctamente.";
        navigate(`/notification?type=confirmation&message=${encodeURIComponent(messageSuccess)}`);
        
    } catch (err) {
        setError("El usuario ya existe o los datos son incorrectos.");
    } finally {
        setIsPending(false);
    }
}

    return (
        <div className="welcome-hero d-flex align-items-center py-5">
            <Container className="px-4 px-lg-5 my-5">
                <div className="row gx-4 gx-lg-5 align-items-center justify-content-center">
                    <div className="col-md-6">
                        <div className="card mb-4 rounded-3 shadow-sm border-primary">
                            <div className="modal-content rounded-4 shadow">
                                <div className="modal-header p-5 pb-4 border-bottom-0 justify-content-center">
                                    <h1 className="fw-bold mb-0 fs-2">Registro del usuario</h1>
                                </div>
                                <div className="modal-body p-5 pt-0">
                                    
                                    <Form onSubmit={handleSubmit} className="mb-4">
                                        
                                        <div className="d-flex justify-content-center mb-4">
                                            <img 
                                                src="/perfil-sin-foto.png"
                                                alt="Profile avatar" 
                                                className="me-3 rounded-circle" 
                                                style={{ height: '80px', width: '80px', objectFit: 'cover' }} 
                                            />
                                        </div>

                                        <Form.Floating className="mb-3">
                                            <Form.Control 
                                                type="text" 
                                                placeholder="Nombre de usuario" 
                                                name="name" 
                                                required 
                                                disabled={isPending} 
                                            />
                                            <label htmlFor="name">Nombre de usuario</label>
                                        </Form.Floating>

                                        <Form.Floating className="mb-4">
                                            <Form.Control 
                                                type="password" 
                                                placeholder="Contraseña" 
                                                name="password" 
                                                required 
                                                disabled={isPending} 
                                            />
                                            <label htmlFor="password">Contraseña</label>
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
                                            {isPending ? "Registrando..." : "Aceptar"}
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