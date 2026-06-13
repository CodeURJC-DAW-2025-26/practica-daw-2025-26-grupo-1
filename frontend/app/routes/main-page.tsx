import {Link} from "react-router";
import {Container} from "react-bootstrap";

export default function MainPage() {

    return (
        <div className="welcome-hero d-flex align-items-center py-5 text-center text-white">

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

                    <div className="text-center">
                        <Link to="/sections" className="btn btn-success px-4 py-2fs-5">
                            Empezar
                        </Link>
                    </div>

                </div>
            </Container>
        </div>
    );
}