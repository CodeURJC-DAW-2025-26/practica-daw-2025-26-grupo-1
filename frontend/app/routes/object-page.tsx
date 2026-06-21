import { useParams, useNavigate, Link } from "react-router";
import type { Route } from "./+types/object-page";
import { Container, Card, Button, Row, Col, Badge, Form, Spinner } from "react-bootstrap";
import { ArrowLeft, Check2, BookmarkPlus, CloudUpload, Trash } from "react-bootstrap-icons";
import { useEffect, useState } from "react";
import { useUserStore } from "~/stores/user-store";
import { getMuseumObject, replaceMuseumObject } from "~/services/museum-object-service";
import { replaceImageFile } from "~/services/image-service";
import { markObjectAsSeen } from "~/services/user-service";
import { deleteNote, getNotesByUser } from "~/services/note-service";
import type { MuseumObjectDTO } from "~/dtos/MuseumObjectDTO";


const subCategoriesConfig: Record<string, string[]> = {
    fish: ["Agua dulce", "Mar", "Abisales"],
    insects: ["Terrestres", "Aéreos", "Acuáticos"],
    fossils: ["Fósiles", "Minerales"],
    art: ["Pintura", "Escultura", "Cerámica"]
};

export async function clientLoader({ params }: Route.ClientLoaderArgs) {
    const data = await getMuseumObject(Number(params.id!));
    return Array.isArray(data) ? data[0] : data;
}

export default function ObjectDetail({ loaderData }: Route.ComponentProps) {
    const navigate = useNavigate();
    const { type } = useParams<{ type: string; id: string }>();

    const museumObject = loaderData as MuseumObjectDTO;
    const { user, loadLoggedUser } = useUserStore();

    const isAdmin = user?.roles?.includes("ADMIN");
    const isLogged = user ? true : false;

    const [imageVersion, setImageVersion] = useState(0);
    const [isSeenState, setIsSeenState] = useState(museumObject.isSeen);    
    const [markingAsSeen, setMarkingAsSeen] = useState(false);
    const [notesList, setNotesList] = useState<any[]>([]);

    useEffect(() => {
        if (isLogged && user && user.seen && museumObject.id) {
            const isAlreadySeen = user.seen.some((element) => element.id === museumObject.id);
            if (isAlreadySeen) {
                setIsSeenState(true);
            }
        }
    }, [user, museumObject.id, isLogged]);

    useEffect(() => {
        async function fetchUserNotes() {
            if (isLogged && museumObject.id) {
                try {
                    const result = await getNotesByUser(0);
                    
                    const filteredNotes = result.items.filter((note: any) => 
                        note.museumObject && note.museumObject.id === museumObject.id);

                    setNotesList(filteredNotes);
                } catch (error) {
                    console.error("Error cargando las notas del usuario:", error);
                }
            } else {
                setNotesList([]);
            }
        }

        fetchUserNotes();
    }, [museumObject.id, isLogged]);

    const [editForm, setEditForm] = useState({
        objectName: museumObject.objectName,
        groupName: museumObject.groupName,
        category: museumObject.category,
        technicalData: museumObject.technicalData,
        description: museumObject.description,
        type: museumObject.type
    });
    const [saving, setSaving] = useState(false);

    const backgrounds: Record<string, string> = {
        fish: "/fondo-marino-siluetas.png",
        insects: "/fondo-insectos-siluetas.png",
        fossils: "/fondo-fosiles-siluetas.png",
        art: "/fondo-secundario-arte.png",
    };


    const handleChange = (
        e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>
    ) => {
        setEditForm({
            ...editForm,
            [e.target.name]: e.target.value
        });
    };

    const handleImageChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files[0]) {
            try {
                if (museumObject.image && museumObject.image.id) {
                    await replaceImageFile(museumObject.image.id, e.target.files[0]);
                    setImageVersion((prev) => prev + 1);
                } else {
                    alert("Este objeto no tiene una imagen base que reemplazar.");
                }
            } catch (error) {
                console.error("Error al actualizar la imagen:", error);
                alert("No se ha podido guardar la imagen.");
            }
        }
    };

    const handleSaveChanges = async () => {
        setSaving(true);
        try {
            await replaceMuseumObject(
                museumObject.id,
                editForm.objectName,
                editForm.groupName,
                editForm.technicalData,
                editForm.description,
                editForm.type,
                editForm.category
            );
            const message = "Objeto actualizado con éxito.";
            navigate(`/notification?type=confirmation&message=${encodeURIComponent(message)}`);
        } catch (error) {
            console.error("Error al actualizar el objeto:", error);
        } finally {
            setSaving(false);
        }
    };

    const handleMarkAsSeen = async () => {
        if (isSeenState || markingAsSeen) return;
        setMarkingAsSeen(true);
        try {
            await markObjectAsSeen(museumObject.id);
            setIsSeenState(true);
            
            await loadLoggedUser(); 
            
        } catch (error) {
            console.error("Error al marcar el objeto como visto:", error);
        } finally {
            setMarkingAsSeen(false);
        }
    };

    const handleDeleteNote = async (noteId: number) => {
        try {
            await deleteNote(noteId);
            setNotesList((prevNotes) => prevNotes.filter((n) => n.id !== noteId));
            const messageSuccess = "Nota eliminada correctamente.";
            navigate(`/notification?type=confirmation&message=${encodeURIComponent(messageSuccess)}`);
        } catch (error) {
            const messageSuccess = "No se ha podido eliminar la nota.";
            navigate(`/notification?type=confirmation&message=${encodeURIComponent(messageSuccess)}`);
        }
    };

    return (
        <div className="min-vh-100 py-4 text-white"
            style={{
                backgroundImage: `linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.6)), url(${backgrounds[type || "fish"] || backgrounds.fish})`,
                backgroundSize: "cover",
                backgroundPosition: "center",
                backgroundAttachment: "fixed",
                backgroundRepeat: "no-repeat"
            }}>

            <Container className="py-2" style={{ maxWidth: "950px" }}>

                <Card className="shadow-lg border-0 bg-white text-dark p-3 mb-4 rounded-4">
                    <Card.Body>
                        {isAdmin ? (
                            <>
                                <Form.Group className="mb-2">
                                    <Form.Label className="fw-bold m-0 text-muted">Nombre:</Form.Label>
                                    <Form.Control type="text" name="objectName" value={editForm.objectName} onChange={handleChange} className="fs-3 fw-bold" />
                                </Form.Group>

                                <Form.Group className="mb-2">
                                    <Form.Label className="fw-bold m-0 text-muted">Dato de interés (Grupo):</Form.Label>
                                    <Form.Control type="text" name="groupName" value={editForm.groupName} onChange={handleChange} />
                                </Form.Group>


                                <Form.Group className="mb-2">
                                    <Form.Label className="fw-semibold text-secondary">Tipo:</Form.Label>
                                    <Form.Control type="text" value={editForm.type} disabled className="bg-light text-muted border-2 rounded-3" />
                                </Form.Group>

                                <Form.Group>
                                    <Form.Label className="fw-semibold text-secondary">Categoría:</Form.Label>
                                    <Form.Select
                                        name="category"
                                        value={editForm.category}
                                        onChange={handleChange}
                                        disabled={saving}
                                        className="border-2 rounded-3"
                                    >
                                        {(subCategoriesConfig[type || "fish"] || subCategoriesConfig.fish).map((sub) => (
                                            <option key={sub} value={sub}>
                                                {sub}
                                            </option>
                                        ))}
                                    </Form.Select>
                                </Form.Group>
                            </>
                        ) : (
                            <>
                                <h1 className="display-5 fw-bolder m-0">{museumObject.objectName}</h1>
                                <p className="text-muted fs-5 m-0 mt-1">{museumObject.groupName}</p>
                                <Badge bg="secondary" className="mt-2 px-3 py-2 fs-6 fw-normal rounded-3">
                                    {museumObject.category}
                                </Badge>
                            </>
                        )}
                    </Card.Body>
                </Card>

                <Row className="g-4 mb-4">
                    <Col md={6} xs={12}>
                        <Card className="shadow-lg border-0 rounded-4 overflow-hidden h-100 position-relative d-flex flex-column align-items-center justify-content-center bg-dark">
                            <Card.Img
                                src={museumObject.image?.id ? `/api/v1/images/${museumObject.image.id}/media?v=${imageVersion}` : "/no_image.png"}
                                className="w-100 h-100"
                                style={{ objectFit: "cover", minHeight: "320px", maxHeight: "400px" }}
                                onError={(e: any) => { e.target.src = "/no_image.png"; }}
                            />

                            {isAdmin && (
                                <div className="p-3 bg-dark bg-opacity-50 w-100 text-center border-top border-secondary">
                                    <Form.Label htmlFor="image-upload" className="btn btn-secondary m-0 px-3 py-1 fw-semibold d-inline-flex align-items-center gap-2 rounded-3 text-white cursor-pointer">
                                        <CloudUpload size={18} />
                                        Cambiar imagen
                                    </Form.Label>
                                    <input id="image-upload" type="file" accept="image/*" className="d-none" onChange={handleImageChange} />
                                </div>
                            )}
                        </Card>
                    </Col>

                    <Col md={6} xs={12}>
                        <Card className="shadow-lg border-0 bg-secondary bg-opacity-75 text-white rounded-4 overflow-hidden h-100">
                            <div className="bg-secondary p-3 text-center border-bottom border-secondary">
                                <h5 className="m-0 fw-bold fs-4">Ficha técnica</h5>
                            </div>
                            <Card.Body className="bg-white text-dark p-4 d-flex flex-column align-items-stretch justify-content-start">
                                {isAdmin ? (
                                    <Form.Group className="w-100 h-100">
                                        <Form.Label className="fw-semibold text-muted">Datos:</Form.Label>
                                        <Form.Control as="textarea" rows={6} name="technicalData" value={editForm.technicalData} onChange={handleChange} className="h-75" />
                                    </Form.Group>
                                ) : (
                                    <p className="fs-5 m-0 lh-base">{museumObject.technicalData}</p>
                                )}
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>

                <Card className="shadow-lg border-0 bg-white text-dark p-3 mb-4 rounded-4">
                    <Card.Body>
                        {isAdmin ? (
                            <Form.Group>
                                <Form.Label className="fw-semibold text-muted">Información:</Form.Label>
                                <Form.Control as="textarea" rows={5} name="description" value={editForm.description} onChange={handleChange} />
                            </Form.Group>
                        ) : (
                            <p className="fs-5 m-0 lh-base">{museumObject.description}</p>
                        )}
                    </Card.Body>
                </Card>

                <div className="d-flex justify-content-center gap-3 my-4">
                    {isAdmin ? (
                        <>
                            <Link to={`/objects/${type}`} className="btn btn-danger px-4 py-2 fw-semibold d-flex align-items-center gap-2 rounded-3 fs-5">
                                <ArrowLeft />
                                Volver
                            </Link>
                            <Button variant="success" onClick={handleSaveChanges} disabled={saving} className="px-4 py-2 fw-semibold d-flex align-items-center gap-2 rounded-3 fs-5">
                                <Check2 size={22} />
                                {saving ? "Guardando..." : "Aceptar cambios"}
                            </Button>
                        </>
                    ) : (
                        <>
                            <Link to={`/objects/${type}`} className="btn btn-danger px-4 py-2 fw-semibold d-flex align-items-center gap-2 rounded-3 fs-5">
                                <ArrowLeft />
                                Volver
                            </Link>

                            {isLogged && (
                                <>
                                    <Button
                                        variant={isSeenState ? "secondary" : "success"}
                                        className="px-4 py-2 fw-semibold d-flex align-items-center gap-2 rounded-3 fs-5 text-white"
                                        onClick={isSeenState ? undefined : handleMarkAsSeen}
                                        disabled={isSeenState || markingAsSeen}
                                        style={{
                                            cursor: isSeenState ? "not-allowed" : "pointer",
                                            opacity: isSeenState ? 0.75 : 1
                                        }}
                                    >
                                        {markingAsSeen ? (
                                            <>
                                                <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" />
                                                Procesando...
                                            </>
                                        ) : (
                                            <>
                                                <Check2 size={22} />
                                                {isSeenState ? "Ya visto" : "Marcar como visto"}
                                            </>
                                        )}
                                    </Button>

                                    <Link to={`/new-note/${type}/${museumObject.id}/new`} className="btn btn-secondary px-4 py-2 fw-semibold d-flex align-items-center gap-2 rounded-3 fs-5">
                                        <BookmarkPlus size={20} />
                                        Añadir nota
                                    </Link>
                                </>
                            )}
                        </>
                    )}
                </div>

                {isLogged && !isAdmin && (
                    <Card className="shadow-lg border-0 bg-white text-dark p-4 mt-4 text-center rounded-4">
                        <Card.Body>
                            <h2 className="display-6 fw-bold m-0">Tus notas:</h2>
                            {notesList && notesList.length > 0 ? (
                                <div className="text-start mt-4">
                                    {notesList.map((note) => (
                                        <Card key={note.id} className="mb-2 bg-light border-1">
                                            <Card.Body className="py-2 fs-5 d-flex justify-content-between align-items-center">
                                                <span>{note.text}</span>
                                                <Button
                                                    variant="outline-danger"
                                                    size="sm"
                                                    className="border-0 rounded-3 p-2 d-flex align-items-center justify-content-center"
                                                    onClick={() => handleDeleteNote(note.id)}
                                                    title="Delete note"
                                                >
                                                    <Trash size={18} />
                                                </Button>
                                            </Card.Body>
                                        </Card>
                                    ))}
                                </div>
                            ) : (
                                <p className="text-muted fs-5 m-0 mt-3">Aún no has escrito ninguna nota sobre este objeto.</p>
                            )}
                        </Card.Body>
                    </Card>
                )}

            </Container>
        </div>
    );
}