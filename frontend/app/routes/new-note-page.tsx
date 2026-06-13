import { useParams, useNavigate, Link } from "react-router";
import { Container, Card, Form, Button, Spinner } from "react-bootstrap";
import { useActionState } from "react";
import { ArrowLeft, Check2 } from "react-bootstrap-icons";
import { createNote } from "~/services/note-service";
import { checkPermission, requiredOnlyStandardUser } from "~/services/route-guards-service";
import type { Route } from "./+types/new-note-page";


export async function clientLoader({ params }: Route.ClientLoaderArgs) {

    const navigate = useNavigate();
    const currentUser = await requiredOnlyStandardUser();

    if (params.noteId) {
        
            const note = await getNote(Number(params.noteId));

            if (!note) {
                const message = "La nota que buscas no existe.";
                const encodeMessage = encodeURIComponent(message);
                navigate(`/notification?type=error&message=${encodeMessage}`);
            }

            checkPermission(currentUser, note?.userId);

            return note;
        
    }

    return null;
}


export default function NewNotePage() {

    const navigate = useNavigate();

    const { type, objectId, noteId } = useParams<{
        type: string;
        objectId: string;
        noteId: string;
    }>();


    async function saveNoteAction(prevState: any, formData: FormData) {

        const text = formData.get("noteText") as string;

        try {
            await createNote(Number(objectId), Number(noteId), text);

            const message = "Nota creada con éxito.";
            const encodeMessage = encodeURIComponent(message);
            navigate(`/notification?type=confirmation&message=${encodeMessage}`);

            return { success: true, error: null }
        } catch {
            const message = "Se ha producido un error al crear la nota.";
            const encodeMessage = encodeURIComponent(message);
            navigate(`/notification?type=error&message=${encodeMessage}`);

            return { success: false, error: "The note could not be created. Please try again.", };
        }
    }


    const [state, formAction, isPending] = useActionState(saveNoteAction, null);

    const backgrounds: Record<string, string> = {
        fish: "/fondo-marino-siluetas.png",
        insects: "/fondo-insectos-siluetas",
        fossils: "/fondo-fosiles-siluetas",
        art: "/fondo-secundario-arte",
    };

    const actualBack = backgrounds[type || "fish"] || backgrounds.fish;

    return (
        <div className="min-vh-100 d-flex flex-column justify-content-between text-white"
            style={{
                backgroundImage: `linear-gradient(rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.4)), url(${actualBack})`,
                backgroundSize: "cover",
                backgroundPosition: "center",
                backgroundRepeat: "no-repeat"
            }}>

            <Container className="d-flex justify-content-center align-items-center flex-grow-1 py-4">
                <Card className="shadow-lg border-0 bg-white text-dark p-4 rounded-4" style={{ maxWidth: "500px", width: "100%" }}>
                    <Card.Body>

                        <Form action={formAction}>
                            <Form.Group className="mb-4">
                                <Form.Label className="fw-semibold text-secondary">
                                    Escribe una nota:
                                </Form.Label>
                                <Form.Control
                                    as="textarea"
                                    name="contenidoNota"
                                    rows={5}
                                    className="border-2 rounded-3"
                                    disabled={isPending}
                                    required
                                />
                            </Form.Group>

                            {state?.error && <p className="text-danger small text-center">{state.error}</p>}

                            <div className="d-grid">

                                <Link to="/sections" className="btn btn-danger px-4 py-2 fs-5 d-flex align-items-center">
                                    <ArrowLeft className="me-2" />
                                    Volver
                                </Link>

                                <Button variant="success" type="submit" className="w-100 d-flex justify-content-center align-items-center gap-2" disabled={isPending}>
                                    {isPending ?
                                        <>
                                            <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" className="me-2" />
                                            "Creando nota..."
                                        </>
                                        :
                                        <>
                                            <Check2 className="me-2" />
                                            Crear y guardar
                                        </>
                                    }
                                </Button>
                            </div>
                        </Form>
                    </Card.Body>
                </Card>
            </Container>
        </div>
    );
}