import { useState } from "react";
import { useLocation, useSearchParams } from "react-router"; 
import { Form, Button, InputGroup, Container, Row, Col } from "react-bootstrap";
import { Search } from "react-bootstrap-icons"; 
import { useUserStore } from "~/stores/user-store";

export default function SearchBar({currentType, onSearch, onFilter}: { 
    currentType: string,
    onSearch: (query: string) => void, 
    onFilter: (filter: string) => void }) {
    const location = useLocation(); 
    const [useParams] = useSearchParams();
    const [query, setQuery] = useState("");
    const {user} = useUserStore();

    const roomType = currentType || "fish";

    return (
        <div className="my-8 p-3 bg-dark">
            <Row className="align-items-center">

                {user && (
                    <Col lg={8} md={7} sm={12} className="mb-3 mb-md-0">
                    <div className="d-flex flex-wrap gap-2">
                        
                        {roomType === "fish" && (
                            <>
                                <Button variant="primary" onClick={() => onFilter("Mar")}>Mar</Button>
                                <Button variant="primary" onClick={() => onFilter("Agua dulce")}>Agua dulce</Button>
                                <Button variant="primary" onClick={() => onFilter("Abisales")}>Abisales</Button>
                            </>
                        )}

                        {roomType === "insects" && (
                            <>
                                <Button variant="primary" onClick={() => onFilter("Terrestres")}>Terrestres</Button>
                                <Button variant="primary" onClick={() => onFilter("Aéreos")}>Aéreos</Button>
                                <Button variant="primary" onClick={() => onFilter("Acuáticos")}>Acuáticos</Button>
                            </>
                        )}

                        {roomType === "fossils" && (
                            <>
                                <Button variant="primary" onClick={() => onFilter("Prehistóricos")}>Prehistóricos</Button>
                                <Button variant="primary" onClick={() => onFilter("Minerales")}>Minerales</Button>
                            </>
                        )}

                        {roomType === "art" && (
                            <>
                                <Button variant="primary" onClick={() => onFilter("Pintura")}>Pintura</Button>
                                <Button variant="primary" onClick={() => onFilter("Escultura")}>Escultura</Button>
                                <Button variant="primary" onClick={() => onFilter("Cerámica")}>Cerámica</Button>
                            </>
                        )}
                        
                    </div>
                </Col>
                )}
                

                <Col lg={4} md={4} sm={4} className="ms-auto">
                    <Form onSubmit={(e) => { e.preventDefault(); onSearch(query); }}>
                        <InputGroup>
                            <Form.Control
                                type="text"
                                placeholder="Buscar objeto..."
                                value={query}
                                onChange={(e) => setQuery(e.target.value)}
                            />
                            <Button variant="secondary" type="submit">
                                <Search />
                            </Button>
                        </InputGroup>
                    </Form>
                </Col>

            </Row>


            
        </div>
    );
}