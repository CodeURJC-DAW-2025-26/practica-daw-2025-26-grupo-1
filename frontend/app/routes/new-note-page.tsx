import { useParams, useNavigate, Link } from "react-router";
import { Container, Card, Form, Button, Spinner } from "react-bootstrap";
import { useState } from "react"; 
import { ArrowLeft, Check2 } from "react-bootstrap-icons";
import { createNote } from "~/services/note-service";
import { requiredOnlyStandardUser } from "~/services/route-guards-service";
import type { Route } from "./+types/new-note-page";


// These imports are intended to ensure that the background images appear when accessing the page from /new
import fishSecondBackground from "/fondo-marino-siluetas.png";
import insectSecondBackground from "/fondo-insectos-siluetas.png";
import fossilSecondBackground from "/fondo-fosiles-siluetas.png";
import artSecondBackground from "/fondo-secundario-arte.png";


export async function clientLoader({ params }: Route.ClientLoaderArgs) {
    await requiredOnlyStandardUser();
    return null;
}

export default function NewNotePage() {
    const navigate = useNavigate();
    const { type, objectId } = useParams<{ type: string; objectId: string }>();

    const [isPending, setIsPending] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setIsPending(true);
        setError(null);

        const formData = new FormData(e.currentTarget);
        const text = formData.get("noteContent") as string;

        try {
            await createNote(Number(objectId), text);

            const message = "Nota creada con éxito.";
            const encodeMessage = encodeURIComponent(message);
            navigate(`/notification?type=confirmation&message=${encodeMessage}`);
        } catch (err) {
            setError("No se ha podido crear la nota. Inténtalo de nuevo.");
            setIsPending(false);
        }
    };

    const backgrounds: Record<string, string> = {
        fish: fishSecondBackground,
        insects: insectSecondBackground,
        fossils: fossilSecondBackground,
        art: artSecondBackground,
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

            <Container className="d-flex flex-column justify-content-center align-items-center flex-grow-1 py-4">
                
                <Card className="shadow-lg border-0 bg-white text-dark p-4 rounded-4 mb-4" style={{ maxWidth: "950px", width: "100%" }}>
                    <Card.Body className="p-2">
                        <Form id="noteForm" onSubmit={handleSubmit}>
                            <Form.Group>
                                <Form.Label className="fw-semibold text-secondary fs-5 mb-3">
                                    Escribe una nota:
                                </Form.Label>
                                <Form.Control
                                    as="textarea"
                                    name="noteContent"
                                    rows={5}
                                    className="border-2 rounded-3 fs-5"
                                    disabled={isPending}
                                    required
                                />
                            </Form.Group>

                            {error && <p className="text-danger small text-center mt-3 mb-0">{error}</p>}
                        </Form>
                    </Card.Body>
                </Card>


                <div className="d-flex justify-content-center gap-3" style={{ maxWidth: "950px", width: "100%" }}>
                    <Link to={`/objects/${type}/${objectId}`} className="btn btn-danger px-4 py-2 fs-5 d-flex align-items-center gap-2 rounded-3" style={{ minWidth: "140px", justifyContent: "center" }}>
                        <ArrowLeft />
                        Volver
                    </Link>


                    <Button variant="success" type="submit" form="noteForm" className="px-4 py-2 fs-5 d-flex justify-content-center align-items-center gap-2 rounded-3" style={{ minWidth: "180px" }} disabled={isPending}>
                        {isPending ? (
                            <>
                                <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" />
                                Guardando...
                            </>
                        ) : (
                            <>
                                <Check2 size={22} />
                                Crear y guardar
                            </>
                        )}
                    </Button>
                </div>

            </Container>
        </div>
    );
}