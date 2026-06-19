import { Link, useNavigate, useParams } from "react-router";
import { Container, Form, Button, Alert } from "react-bootstrap";
import { ArrowLeft, Check2 } from "react-bootstrap-icons";
import { useState, useEffect } from "react";
import type { FormEvent } from "react";
import type { Route } from "./+types/profile-page";
import { getUser, updateUser } from "~/services/admin-service";
import { replaceUserImage } from "~/services/user-service";
import { useUserStore } from "~/stores/user-store";
import { requiredLoggedUser, checkPermission } from "~/services/route-guards-service";

export async function clientLoader({ params }: Route.ClientLoaderArgs) {
    const currentUser = await requiredLoggedUser();

    if (params.id) {
        const targetId = Number(params.id);
        checkPermission(currentUser, targetId);
    }
    return null;
}

export default function ProfilePage() {
    const navigate = useNavigate();
    const { id } = useParams();
    const [isPending, setIsPending] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const { user: loggedUser, loadLoggedUser } = useUserStore();

    const [targetUserId, setTargetUserId] = useState<number | null>(null);
    const [username, setUsername] = useState("");

    const [imageVersion, setImageVersion] = useState(0);
    const [userImageId, setUserImageId] = useState<number | undefined>(undefined);

    const [previewImage, setPreviewImage] = useState("/perfil-sin-foto.png");
    const [selectedFile, setSelectedFile] = useState<File | null>(null);

    const getUserAvatarUrl = (imageId: number | undefined, version: number) => {
        if (imageId) {
            return `/api/v1/images/${imageId}/media?v=${version}`;
        }
        return "/perfil-sin-foto.png";
    };

    useEffect(() => {
        if (id) {
            getUser(id)
                .then((fetchedUser) => {
                    setTargetUserId(fetchedUser.id);
                    setUsername(fetchedUser.name);
                    setUserImageId(fetchedUser.userImage?.id);
                    setPreviewImage(getUserAvatarUrl(fetchedUser.userImage?.id, imageVersion));
                })
                .catch(() => setError("Error al cargar los datos del usuario seleccionado."));
        } else if (loggedUser) {
            setTargetUserId(loggedUser.id);
            setUsername(loggedUser.name);
            setUserImageId(loggedUser.userImage?.id);
            setPreviewImage(getUserAvatarUrl(loggedUser.userImage?.id, imageVersion));
        }
    }, [id, loggedUser, imageVersion]);

    function handleFileChange(event: React.ChangeEvent<HTMLInputElement>) {
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

    async function handleUsernameChange(event: React.ChangeEvent<HTMLInputElement>) {
        setUsername(event.target.value);
    }

   

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        if (!targetUserId) return;

        setIsPending(true);
        setError(null);

        try {

        if (selectedFile) {
            const uploadedImage = await replaceUserImage(targetUserId, selectedFile);   
            setImageVersion((prev) => prev + 1); 
        }

        await updateUser(targetUserId, username);

        if (targetUserId === loggedUser?.id) {
            await loadLoggedUser();
        }

        const messageSuccess = "Perfil actualizado correctamente.";
        navigate(`/notification?type=confirmation&message=${encodeURIComponent(messageSuccess)}`);

        } catch (err: any) {
            console.error(err);
            setError(err.message || "Error al actualizar el perfil del usuario.");
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
                                    <h1 className="fw-bold mb-0 fs-2">
                                        {id && Number(id) !== loggedUser?.id ? `Perfil del usuario #${id}` : "Su perfil"}
                                    </h1>
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
                                                accept="image/*"
                                                onChange={handleFileChange}
                                                disabled={isPending}
                                            />
                                        </Form.Group>

                                        <Form.Floating className="mb-4">
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
                                        Al hacer clic en "Aceptar", confirmas la edición del registro.
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
