import Graphic from "~/components/graphic";
import { Col, Row, Container } from "react-bootstrap";
import { ArrowLeft } from "react-bootstrap-icons";
import { Link } from "react-router";
import { getMyStats } from "~/services/user-service";
import type { UserStatisticsDTO } from "~/dtos/UserStatisticsDTO";
import type { CategoryStatsDTO } from "~/dtos/CategoryStatsDTO";
import { checkPermissionAlternative, requiredLoggedUser } from "~/services/route-guards-service";
import type {Route} from "./+types/statistics-page";


export async function clientLoader({ params }: Route.ClientLoaderArgs) {
    const currentUser = await requiredLoggedUser();

    if (params.id) {
        const targetId = Number(params.id);
        checkPermissionAlternative(currentUser, targetId);
    }
    return await getMyStats();
}


export default function StatisticsPage({loaderData}: Route.ComponentProps) {

    const stats = loaderData as UserStatisticsDTO;

    const graphicData = [
        { name: "Peces", value: stats?.globalTotals?.peces || 0 },
        { name: "Insectos", value: stats?.globalTotals?.insectos || 0 },
        { name: "Fósiles", value: stats?.globalTotals?.fosiles || 0 },
        { name: "Obras de arte", value: stats?.globalTotals?.arte || 0 },
    ];

    return (
        <div className="d-flex align-items-center py-5">
            
                <Container className="px-4 px-lg-5 my-5">
                    <div className="position-relative d-flex align-items-center justify-content-center w-100 mb-5">
                        <Link to="/sections" className="btn btn-danger px-4 py-2 fs-5 d-flex align-items-center position-absolute start-0">
                            <ArrowLeft className="me-2" />
                            Volver
                        </Link>
                    

                        <Col className="text-center">
                            <h1 className="mt-4">Estadísticas</h1>
                        </Col> 
                    </div>

                    <h5 className="text-dark fw-bold">Tu progreso:</h5>

                    <Row className="mb-5">

                        <Col xl={3} md={6}>
                            <div className="card bg-primary text-white mb-4 shadow">
                                <div className="card-body">
                                    Peces: {stats?.statsByCategory?.find((s: CategoryStatsDTO) => s.categoryName === "peces")?.percentage || 0} %
                                </div>
                            </div>
                        </Col>
                        <Col xl={3} md={6}>
                            <div className="card bg-success text-white mb-4 shadow">
                                <div className="card-body">
                                    Insectos: {stats?.statsByCategory?.find((s: CategoryStatsDTO) => s.categoryName === "insectos")?.percentage || 0} %
                                </div>
                            </div>
                        </Col>
                        <Col xl={3} md={6}>
                            <div className="card bg-warning text-dark mb-4 shadow">
                                <div className="card-body">
                                    Fósiles: {stats?.statsByCategory?.find((s: CategoryStatsDTO) => s.categoryName === "fosiles")?.percentage || 0} %
                                </div>
                            </div>
                        </Col>
                        <Col xl={3} md={6}>
                            <div className="card bg-danger text-white mb-4 shadow">
                                <div className="card-body">
                                    Arte: {stats?.statsByCategory?.find((s: CategoryStatsDTO) => s.categoryName === "arte")?.percentage || 0} %
                                </div>
                            </div>
                        </Col>

                    </Row>

                    <Row className="justify-content-center">
                        <Col md={10} lg={8}>
                            <Graphic details={graphicData} />
                        </Col>
                    </Row>
                </Container>
        </div >
    );
}