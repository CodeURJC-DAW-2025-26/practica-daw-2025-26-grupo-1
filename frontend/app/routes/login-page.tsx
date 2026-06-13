import { Link, useNavigate } from "react-router";
import { Button, Container, Form, Spinner } from "react-bootstrap";
import type { FormEvent } from "react";
import { useActionState, startTransition } from "react";
import { useUserStore } from "~/stores/user-store";
import { ArrowLeft, Check2 } from "react-bootstrap-icons";


export default function LoginPage() {

    const userStore = useUserStore();
    const navigate = useNavigate();


    async function loginAction(prevState: any, formData: FormData) {
        const username = formData.get("username") as string;
        const password = formData.get("password") as string;

        try {
            await userStore.loginUser(username, password);
            const loggedUser = useUserStore.getState().user;

            if (loggedUser) {
                navigate("/sections");
            } else {
                const message = "Usuario o contraseña incorrectos.";
                const encodeMessage = encodeURIComponent(message);
                navigate(`/notification?type=error&message=${encodeMessage}`);
            }
        } catch {
            const message = "Se ha producido un error al iniciar sesión.";
            const encodeMessage = encodeURIComponent(message);
            navigate(`/notification?type=error&message=${encodeMessage}`);
        }

        return null;
    }

    const [, formAction, isPending] = useActionState(loginAction, null);


    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        const form = event.currentTarget;

        startTransition(() => {
            formAction(new FormData(form));
        });
    }


    return (
        <div className="welcome-hero d-flex align-items-center py-5">

            <Container className="px-4 px-lg-5 my-5">

                <div className="container px-4 px-lg-5 my-5">
                    <div className="row gx-4 gx-lg-5 align-items-center justify-content-center">

                        <div className="col-md-6">

                            <div className="card mb-4 rounded-3 shadow-sm border-primary">

                                <div className="modal-content rounded-4 shadow">
                                    <div className="modal-header p-5 pb-4 border-bottom-0 justify-content-center">
                                        <h1 className="fw-bold mb-0 fs-2">Iniciar sesión</h1>
                                    </div>
                                    <div className="modal-body p-5 pt-0">


                                        <Form className="mb-4" onSubmit={handleSubmit}>
                                            <Form.Group className="mb-3">
                                                <Form.Label>Nombre de usuario</Form.Label>
                                                <Form.Control type="text" name="username" required disabled={isPending} />
                                            </Form.Group>

                                            <Form.Group className="mb-3">
                                                <Form.Label>Contraseña</Form.Label>
                                                <Form.Control type="password" name="password" required disabled={isPending} />
                                            </Form.Group>

                                            <Button variant="success" type="submit" className="w-100 d-flex justify-content-center align-items-center gap-2" disabled={isPending}>
                                                {isPending ?
                                                    <>
                                                        <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" className="me-2" />
                                                        Iniciando tu sesión...
                                                    </>
                                                    :
                                                    <>
                                                        <Check2 className="me-2" />
                                                        Aceptar
                                                    </>
                                                }
                                            </Button>
                                        </Form>

                                        <small className="text-body-secondary text-center d-block">Al hacer clic en "Aceptar", aceptas las
                                            condiciones de uso.</small>
                                    </div>
                                </div>
                            </div>


                            <div className="d-flex justify-content-center gap-3">
                                <Link to="/sections" className="btn btn-danger px-4 py-2 fs-5 d-flex align-items-center" type="button">
                                    <ArrowLeft className="me-2" />
                                    Volver
                                </Link>
                            </div>

                        </div>
                    </div>
                </div>
            </Container>
        </div>

    );
}