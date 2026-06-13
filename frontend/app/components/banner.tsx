import { Container } from "react-bootstrap";

export default function MenuBanner() {
    return (
        <div className="bg-dark text-white py-3 mt-auto">
            <Container className="text-center">
                <h4>Por favor, seleccione una de las secciones que se muestran a continuación:</h4>
            </Container>
        </div>
    );
}