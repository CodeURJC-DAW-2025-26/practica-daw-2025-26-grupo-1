import Container from "react-bootstrap/Container";
import { Nav, Navbar, Dropdown } from "react-bootstrap";
import { Link } from "react-router";
import { useEffect } from "react";
import { useUserStore } from "~/stores/user-store";

const getUserAvatarUrl = (user: any) => {
    if (user?.userImage?.id) {
        return `/api/v1/users/${user.id}/image`; 
    }
    return "/perfil-sin-foto.png"; 
};

export default function Header() {
    const { user, logoutUser, loadLoggedUser } = useUserStore();

    useEffect(() => {
        loadLoggedUser();
    }, [loadLoggedUser]);

    const handleLogout = async () => {
        await logoutUser();
    };

    const isAdmin = user?.roles?.includes("ADMIN");
    const isUser = user?.roles?.includes("USER");

    return (
        <>
            <Navbar expand="lg" bg="dark" data-bs-theme="dark">
                <Container fluid>

                    <Nav className="me-auto">
                        <Nav.Link as={Link} to="/sections">Página principal</Nav.Link>
                        <Nav.Link as={Link} to="/objects/fish">Peces</Nav.Link>
                        <Nav.Link as={Link} to="/objects/insects">Insectos</Nav.Link>
                        <Nav.Link as={Link} to="/objects/fossils">Fósiles</Nav.Link>
                        <Nav.Link as={Link} to="/objects/art">Obras de arte</Nav.Link>
                    </Nav>

                    <Nav>
                        {!user && (
                            <>
                                <Link to="/login" className="btn btn-outline-light me-2">Iniciar sesión</Link>
                                <Link to="/register" className="btn btn-warning">Registrarse</Link>
                            </>
                        )}

                        {user && (
                            <Dropdown align="end">
                                <Dropdown.Toggle variant="dark" id="dropdown-user" className="border-0 p-0 d-flex align-items-center">
                                    <img 
                                        src={getUserAvatarUrl(user)} 
                                        alt="Avatar de perfil" 
                                        className="rounded-circle"
                                        style={{ width: '40px', height: '40px', objectFit: 'cover' }}
                                        onError={(e) => {
                                            (e.target as HTMLImageElement).src = "/perfil-sin-foto.png";
                                        }}
                                    />
                                </Dropdown.Toggle>

                                <Dropdown.Menu>
                                    <Dropdown.Item as={Link} to={`/profile/${user.id}`}>Mi perfil</Dropdown.Item>
                                    <Dropdown.Divider />
                                    
                                    {isUser && !isAdmin && (
                                        <>
                                            <Dropdown.Item as={Link} to={`/statistics/${user.id}`}>Estadísticas</Dropdown.Item>
                                            <Dropdown.Divider />
                                        </>
                                    )}
                                    
                                    {isAdmin && (
                                        <>
                                            <Dropdown.Item as={Link} to="/list-users">Lista de usuarios</Dropdown.Item>
                                            <Dropdown.Divider />
                                        </>
                                    )}
                                    
                                    <Dropdown.Item onClick={handleLogout}>Cerrar sesión</Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>
                        )}

                    </Nav>
                </Container>
            </Navbar>
        </>
    );
}