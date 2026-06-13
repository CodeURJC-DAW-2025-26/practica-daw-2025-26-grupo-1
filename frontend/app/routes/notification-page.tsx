import { Link, useSearchParams } from "react-router";
import { Container } from "react-bootstrap";
import { ArrowLeft, Check2 } from "react-bootstrap-icons";

export default function NotificationPage() {

    const [searchParams] = useSearchParams();

    const type = searchParams.get("type") || "confirmation";
    const message = searchParams.get("message") || "Operación realizada con éxito.";

    const variantColor = type === "error" ? "danger" : "success";
    const title = type === "error" ? "Error en el sistema" : "Operación realizada";
    const backButton = type === "error" ? "Volver" : "Aceptar";

    const icon = type === "error" ? <ArrowLeft className="me-2"/> : <Check2 className="me-2"/>;


    return (
        <div
            className="welcome-hero d-flex align-items-center py-5">

            <Container className="px-4 px-lg-5 my-5">
                <div className="row gx-4 gx-lg-5 align-items-center justify-content-center">

                    <div className="col-md-6">

                        <div className="card mb-4 rounded-3 shadow-sm">
                            <div className={`card-header py-3 text-center bg-${variantColor} text-white`}>
                                <h4 className="my-0 fw-normal">{title}</h4>
                            </div>
                            <div className="card-body ">

                                <p className="card-text">{message}</p>
                                <div className="d-flex justify-content-center gap-3 mt-3">

                                    <div className="text-center">
                                        <Link to="/sections" className={`btn btn-${variantColor} px-4 py-2 fs-5 d-flex align-items-center`}
                                            >
                                            {icon}

                                            {backButton}
                                        </Link>
                                    </div>

                                </div>

                            </div>
                        </div>

                    </div>

                </div>
            </Container>
        </div>
    );
}