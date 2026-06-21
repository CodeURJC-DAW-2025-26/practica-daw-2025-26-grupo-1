import { Container, Card, Button, Badge } from "react-bootstrap";
import type { MuseumObjectBasicDTO } from "~/dtos/MuseumObjectBasicDTO";

interface ObjectCardProps {
    object?: MuseumObjectBasicDTO;
    isMenu?: boolean;
    title?: string;
    image?: string;
    menuCategory: string[];
    onAccess: () => void;
    isAdmin?: boolean;
    onEliminate?: (id: number) => void;
}

export default function ObjectCard({ object, isMenu = false, title, image, menuCategory = [], onAccess, isAdmin = false, onEliminate }: ObjectCardProps) {

    let imageUrl = "";

    if (isMenu && image) {
        imageUrl = image;
    } else if (object) {
        if (object.image && object.image.id) {
            imageUrl = `/api/v1/images/${object.image.id}/media`;
        } else {
            imageUrl = "/no_image.png";
        }
    } else {
        imageUrl = "/no_image.png";
    }

    const titleCard = isMenu ? title : object?.objectName;

    return (
        <Card className="h-100 shadow-sm border-0 bg-white rounded-3 overflow-hidden text-center">

            <Card.Img
                variant="top"
                src={imageUrl}
                style={{ height: "200px", objectFit: "cover" }}
            />

            <Card.Body className="d-flex flex-column p-3 align-items-center justify-content-between">

                <Card.Title className="fw-bold fs-5 mb-2 text-dark">
                    {titleCard}
                </Card.Title>

                <div className="d-flex flex-wrap gap-1 justify-content-center mb-3">

                    {isMenu ? (
                        menuCategory.map((cat, index) => (
                            <Badge key={index} bg="secondary" className="px-2 py-1 opacity-75 fs-7">
                                {cat}
                            </Badge>
                        ))
                    ) : (

                        object?.category && (
                            <Badge bg="secondary" className="px-2 py-1 opacity-75 fs-7">
                                {object.category}
                            </Badge>
                        )
                    )}
                </div>

                <div className="d-flex gap-2 w-100 justify-content-center mt-auto">

                    <Button
                        variant="outline-dark"
                        className="rounded-3 px-4 py-1 fs-6"
                        onClick={onAccess}
                    >
                        Seleccionar
                    </Button>

                    {isAdmin && !isMenu && object && onEliminate && (
                        <Button
                            variant="danger"
                            className="rounded-3 px-2 py-1"
                            onClick={() => onEliminate(object.id)}
                        >
                            Borrar
                        </Button>
                    )}

                </div>
            </Card.Body>
        </Card>
    );

}