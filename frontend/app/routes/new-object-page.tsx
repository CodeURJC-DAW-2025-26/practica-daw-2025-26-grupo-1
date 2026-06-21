import { useParams, useNavigate, Link } from "react-router";
import { Container, Card, Form, Button, Spinner, Row, Col } from "react-bootstrap";
import { useActionState } from "react";
import { ArrowLeft, Check2 } from "react-bootstrap-icons";
import { createMuseumObject, createObjectImage } from "~/services/museum-object-service";
import type { Route } from "./+types/new-object-page.tsx"
import { requiredAdmin } from "~/services/route-guards-service";


export async function clientLoader(_: Route.ClientLoaderArgs) {
    await requiredAdmin();
    return null;
}


export default function NewObjectPage() {
    const navigate = useNavigate();
    const { type } = useParams<{ type: string }>();

    const categoryTranslation: Record<string, string> = {
        fish: "peces",
        insects: "insectos",
        fossils: "fósiles",
        art: "arte"
    };

    const currentCategory = categoryTranslation[type || "fish"] || "peces";

    const subCategoriesConfig: Record<string, string[]> = {
        fish: ["Agua dulce", "Mar", "Abisales"],
        insects: ["Terrestres", "Aéreos", "Acuáticos"],
        fossils: ["Fósiles", "Minerales"],
        art: ["Pintura", "Escultura", "Cerámica"]
    };

    async function saveObjectAction(prevState: any, formData: FormData) {
        const objectName = formData.get("objectName") as string;
        const groupName = formData.get("groupName") as string;
        const technicalData = formData.get("technicalData") as string;
        const description = formData.get("description") as string;
        const subCategory = formData.get("subCategory") as string;

        const imageFile = formData.get("imageFile") as File | null;

        try {
            const newObject = await createMuseumObject(
                objectName,
                groupName,
                technicalData,
                description,
                currentCategory,
                subCategory
            );

            if (imageFile && imageFile.size > 0 && imageFile.name !== "") {
                await createObjectImage(newObject.id, imageFile);
            }

            const message = "Objeto creado con éxito.";
            navigate(`/notification?type=confirmation&message=${encodeURIComponent(message)}`);
            return { success: true, error: null };

        } catch (error: any) {
            console.error(error);
            return {
                success: false,

                error: error instanceof Error ? error.message : "Error desconocido al crear."
            };
        }
    }

    const [state, formAction, isPending] = useActionState(saveObjectAction, null);

    const backgrounds: Record<string, string> = {
        fish: "/fondo-marino-siluetas.png",
        insects: "/fondo-insectos-siluetas.png",
        fossils: "/fondo-fosiles-siluetas.png",
        art: "/fondo-secundario-arte.png",
    };
    const actualBack = backgrounds[type || "fish"] || backgrounds.fish;

    return (
        <div className="min-vh-100 py-5 text-white"
            style={{
                backgroundImage: `linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.6)), url(${actualBack})`,
                backgroundSize: "cover",
                backgroundPosition: "center",
                backgroundAttachment: "fixed",
                backgroundRepeat: "no-repeat"
            }}>

            <Container className="py-4">
                <Form action={formAction}>
                    <Row className="g-4 justify-content-center">

                        <Col lg={6} md={12}>
                            <Card className="shadow-lg border-0 bg-white text-dark p-3 h-0 rounded-4">
                                <Card.Body className="d-flex flex-column gap-3">
                                    <Form.Group>
                                        <Form.Label className="fw-semibold text-secondary">Nombre:</Form.Label>
                                        <Form.Control type="text" name="objectName" required disabled={isPending} className="border-2 rounded-3" />
                                    </Form.Group>

                                    <Form.Group>
                                        <Form.Label className="fw-semibold text-secondary">Dato de interés:</Form.Label>
                                        <Form.Control type="text" name="groupName" required disabled={isPending} className="border-2 rounded-3" />
                                    </Form.Group>

                                    <Form.Group>
                                        <Form.Label className="fw-semibold text-secondary">Tipo:</Form.Label>
                                        <Form.Control type="text" value={currentCategory} disabled className="bg-light text-muted border-2 rounded-3" />
                                    </Form.Group>

                                    <Form.Group>
                                        <Form.Label className="fw-semibold text-secondary">Categoría:</Form.Label>
                                        <Form.Select name="subCategory" disabled={isPending} className="border-2 rounded-3">
                                            {(subCategoriesConfig[type || "fish"] || subCategoriesConfig.fish).map((sub) => (
                                                <option key={sub} value={sub}>
                                                    {sub}
                                                </option>
                                            ))}
                                        </Form.Select>
                                    </Form.Group>

                                </Card.Body>
                            </Card>


                            <Form.Group className="mt-2 text-center border p-3 rounded-3 bg-light">
                                <Form.Label className="fw-semibold text-secondary d-block">Imagen del objeto:</Form.Label>
                                <Form.Control type="file" name="imageFile" accept="image/*" disabled={isPending} className="border-2" />
                            </Form.Group>

                        </Col>


                        <Col lg={6} md={12}>
                            <div className="d-flex flex-column gap-4 h-100">

                                <Card className="shadow-lg border-0 bg-secondary bg-opacity-75 text-white rounded-4 overflow-hidden">
                                    <div className="bg-secondary p-3 text-center border-bottom border-secondary">
                                        <h5 className="m-0 fw-bold">Ficha técnica</h5>
                                    </div>
                                    <Card.Body className="bg-white text-dark p-3">
                                        <Form.Group>
                                            <Form.Label className="fw-semibold text-secondary">Datos:</Form.Label>
                                            <Form.Control as="textarea" name="technicalData" rows={4} required disabled={isPending} className="border-2 rounded-3" />
                                        </Form.Group>
                                    </Card.Body>
                                </Card>

                                <Card className="shadow-lg border-0 bg-white text-dark p-3 rounded-4 flex-grow-1">
                                    <Card.Body>
                                        <Form.Group className="h-100 d-flex flex-column">
                                            <Form.Label className="fw-semibold text-secondary">Información:</Form.Label>
                                            <Form.Control as="textarea" name="description" rows={5} required disabled={isPending} className="border-2 rounded-3 flex-grow-1" />
                                        </Form.Group>
                                    </Card.Body>
                                </Card>

                            </div>
                        </Col>
                    </Row>

                    {state?.error && (
                        <p className="text-danger bg-white p-2 rounded shadow-sm small text-center mt-4 mx-auto style={{ maxWidth: '400px' }}">
                            {state.error}
                        </p>
                    )}

                    <div className="d-flex justify-content-center gap-4 mt-5">
                        <Link to={`/objects/${type}`} className="btn btn-danger px-4 py-2 fw-bold d-flex align-items-center gap-2 rounded-3 shadow">
                            <ArrowLeft />
                            Volver
                        </Link>

                        <Button variant="success" type="submit" disabled={isPending} className="px-4 py-2 fw-bold d-flex align-items-center gap-2 rounded-3 shadow">
                            {isPending ? (
                                <>
                                    <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" />
                                    Guardando...
                                </>
                            ) : (
                                <>
                                    <Check2 size={20} />
                                    Crear y guardar
                                </>
                            )}
                        </Button>
                    </div>
                </Form>
            </Container>
        </div>
    );
}