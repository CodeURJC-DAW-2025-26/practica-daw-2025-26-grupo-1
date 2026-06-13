import { useState } from "react"
import { Container, Table, Button, Badge, Spinner } from "react-bootstrap";
import { ArrowLeft, EyeFill } from "react-bootstrap-icons";
import type { Route } from "./+types/list-users-page";
import { Link, useNavigate } from "react-router";
import { getUsers, deleteUser } from "~/services/admin-service";
import type { UserBasicDTO } from "~/dtos/UserBasicDTO";
import { requiredAdmin } from "~/services/route-guards-service";

export async function clientLoader(_: Route.ClientLoaderArgs) {
    await requiredAdmin();
    return await getUsers(0);
}

export default function ListUsersPage({ loaderData }: Route.ComponentProps) {
    const navigate = useNavigate();

    const [users, setUsers] = useState<UserBasicDTO[]>(loaderData?.items || []);
    const [hasMore, setHasMore] = useState(loaderData?.hasNext || false);
    const [currentPage, setCurrentPage] = useState(0);
    const [loading, setLoading] = useState(false);

    console.log("¿Estado de hasMore en React?:", hasMore, "| Datos del Loader:", loaderData);


    const loadMoreUsers = async () => {
        setLoading(true);
        const nextPage = currentPage + 1;
        try {
            const result = await getUsers(nextPage);
            if (result?.items) {
                setUsers([...users, ...result.items]);
                setHasMore(result.hasNext);
                setCurrentPage(nextPage);
            }
        } catch {
            const message = "Se ha producido un error al cargar más usuarios.";
            const encodeMessage = encodeURIComponent(message);
            navigate(`/notification?type=error&message=${encodeMessage}`);
        } finally {
            setLoading(false);
        }
    };

    const handleEliminate = async (id: number) => {
        const confirmDelete = window.confirm("¿Seguro que deseas eliminar a este usuario del sistema?");
        if (!confirmDelete) return;

        try {
            await deleteUser(id);
            setUsers((prevUsers) => prevUsers.filter((u) => u.id !== id));
            const message = "Usuario eliminado correctamente.";
            const encodeMessage = encodeURIComponent(message);
            navigate(`/notification?type=confirmation&message=${encodeMessage}`);
        } catch {
            const message = "Se ha producido un error al eliminar al usuario.";
            const encodeMessage = encodeURIComponent(message);
            navigate(`/notification?type=error&message=${encodeMessage}`);
        }
    };

    return (
        <div className="d-flex flex-column justify-content-between" style={{ minHeight: "85vh" }}>

            <Container className="py-5" style={{ maxWidth: "1000px" }}>

            <div className="d-flex align-items-center mb-4 position-relative justify-content-center">
                <Link to="/sections" className="btn btn-danger position-absolute start-0 d-flex align-items-center">
                    <ArrowLeft className="me-2" /> Volver
                </Link>
                <h1 className="m-0 fw-bold fs-2 text-dark">Gestión de Usuarios</h1>
            </div>

            <div className="card shadow-sm border-0 rounded-3 mb-4">
                <div className="card-header bg-light py-3 border-bottom border-light-subtle">
                    <h5 className="m-0 text-secondary fs-6 fw-semibold">
                        👥 Usuarios registrados en el sistema
                    </h5>
                </div>

                <Table hover className="m-0 align-middle">
                    <thead className="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Roles</th>
                            <th className="text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {users.length > 0 ? (
                            users.map((user: UserBasicDTO) => (
                                <tr key={user.id}>
                                    <td>{user.id}</td>
                                    <td className="fw-bold">{user.name}</td>
                                    <td>
                                        <div className="d-flex gap-1">
                                            {user.roles.map((rol) => (
                                                <Badge key={rol} bg="secondary">
                                                    {rol}
                                                </Badge>
                                            ))}
                                        </div>
                                    </td>
                                    <td className="text-center">
                                        <Link to={`/profile/${user.id}`} className="btn btn-primary btn-sm me-2">
                                            Perfil
                                        </Link>
                                        <Button variant="danger" size="sm" onClick={() => handleEliminate(user.id)}>
                                            Eliminar perfil
                                        </Button>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan={4} className="text-center py-4 text-muted">
                                    No hay usuarios registrados.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </Table>
            </div>

            {hasMore && (
                <div className="text-center mt-4">
                    <Button
                        variant="secondary"
                        onClick={loadMoreUsers}
                        disabled={loading}
                        className="d-inline-flex align-items-center fw-bold text-white px-4 py-2 shadow-sm rounded-pill"
                    >
                        {loading ? (
                            <>
                                <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" className="me-2" />
                                Cargando...
                            </>
                        ) : (
                            <>
                                <EyeFill className="me-2" />
                                Ver más
                            </>
                        )}
                    </Button>
                </div>
            )}

        </Container>
        </div>
        
    );
}