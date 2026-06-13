import "bootstrap/dist/css/bootstrap.min.css";
import "~/app.css";
import { Outlet, useNavigate, useNavigation, isRouteErrorResponse, Link } from "react-router";
import Header from "~/components/header";
import Footer from "~/components/footer";
import type { Route } from "./+types/home";
import Container from "react-bootstrap/esm/Container";
import {ArrowLeft} from "react-bootstrap-icons";


export default function Home() {
  const navigation = useNavigation();
  const isLoading = navigation.state === "loading";

  return (
    <>
      {isLoading && (
        <div className="page-spinner-overlay">
          <div className="dot-spinner" />
        </div>
      )}

      <Header />
      <Outlet />
      <Footer />
    </>
  );
}

export function ErrorBoundary({ error }: Route.ErrorBoundaryProps) {
  const navigate = useNavigate();

  let errorMessage = error instanceof Error ? error.message : "Se ha producido un error en el sistema.";

  if (isRouteErrorResponse(error)) {
    errorMessage = error.data || error.statusText;
  } else if (error instanceof Error) {
    errorMessage = error.message;
  }

  return (
    <div
            className="welcome-hero d-flex align-items-center py-5">

            <Container className="px-4 px-lg-5 my-5">
                <div className="row gx-4 gx-lg-5 align-items-center justify-content-center">

                    <div className="col-md-6">

                        <div className="card mb-4 rounded-3 shadow-sm">
                            <div className={`card-header py-3 text-center bg-danger text-white`}>
                                <h4 className="my-0 fw-normal">Error en el sistema</h4>
                            </div>
                            <div className="card-body ">

                                <p className="card-text">{errorMessage}</p>
                                <div className="d-flex justify-content-center gap-3 mt-3">

                                    <div className="text-center">
                                        <Link to="/sections" className={`btn btn-danger px-4 py-2 fs-5 d-flex align-items-center`}
                                            >
                                            <ArrowLeft className="me-2"/>

                                            Volver
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