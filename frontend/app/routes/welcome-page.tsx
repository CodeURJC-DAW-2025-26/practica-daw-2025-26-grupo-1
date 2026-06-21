import { Col, Container, Row } from "react-bootstrap";
import { useNavigate } from "react-router";
import MenuBanner from "~/components/banner";
import ObjectCard from "~/components/object-card";


export default function WelcomePage() {

    const navigate = useNavigate();

    return (
        
        <>
            <div className="welcome-hero-adapted d-flex align-items-center py-5 text-center text-white">

                <Container className="px-4 px-lg-5 my-5">
                    <div className="text-center text-white">

                        <div className="container h-100 d-flex flex-column align-items-center justify-content-center">
                            <h1 className="display-4 fw-bolder">¡Bienvenido a Museoteca!</h1>
                        </div>

                        <div className="my-5">
                            <h4 className="text-center text-white">
                                El lugar donde podrá encontrar y aprender acerca de algunas temáticas
                                que seguro que le resultarán interesantes.
                            </h4>
                        </div>

                    </div>
                </Container>

            </div>

            <MenuBanner />

            <div className="second-hero-adapted d-flex align-items-center py-5 text-center">
                <Container className="px-4 px-lg-5 my-5">
                    <Row className="g-4 justify-content-center">
                        <Col xs={20} sm={10} md={3}>
                            <ObjectCard
                                isMenu={true}
                                title="Peces"
                                image="icons/logo-pez.png"
                                menuCategory={["Mar", "Agua dulce", "Abisales"]}
                                onAccess={() => navigate("/objects/fish")}
                            />
                        </Col>

                        <Col xs={12} sm={6} md={3}>
                            <ObjectCard
                                isMenu={true}
                                title="Insectos"
                                image="icons/logo-mariposa.png"
                                menuCategory={["Terrestres", "Aéreos", "Acuáticos"]}
                                onAccess={() => navigate("/objects/insects")}
                            />
                        </Col>

                        <Col xs={12} sm={6} md={3}>
                            <ObjectCard
                                isMenu={true}
                                title="Fósiles"
                                image="icons/logo-fosil.png"
                                menuCategory={["Fósiles", "Minerales"]}
                                onAccess={() => navigate("/objects/fossils")}
                            />
                        </Col>

                        <Col xs={12} sm={6} md={3}>
                            <ObjectCard
                                isMenu={true}
                                title="Obras de arte"
                                image="icons/logo-pintura.png"
                                menuCategory={["Pintura", "Escultura", "Cerámica"]}
                                onAccess={() => navigate("/objects/art")}
                            />
                        </Col>
                    </Row>
                </Container>
            </div>
        </>
    );
}