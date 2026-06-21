import { Link, useParams } from "react-router";
import type { Route } from "./+types/section-page";
import { useState, useEffect } from "react";
import { Container, Row, Col, Button, Spinner } from "react-bootstrap";
import { useUserStore } from "~/stores/user-store";
import { getMuseumObjects, deleteMuseumObject } from "~/services/museum-object-service";
import type { MuseumObjectBasicDTO } from "~/dtos/MuseumObjectBasicDTO";
import { Plus, EyeFill } from "react-bootstrap-icons";
import SearchBar from "~/components/search-bar";
import ObjectCard from "~/components/object-card";
import { useNavigate } from "react-router";


// These imports are intended to ensure that the background images and logos appear when accessing the page from /new
import fishBackground from "/fondo-marino.png";
import fishSecondBackground from "/fondo-marino-siluetas.png";
import fishLogo from "/icons/logo-pez.png";
import insectBackground from "/fondo-insectos.png";
import insectSecondBackground from "/fondo-insectos-siluetas.png";
import insectLogo from "/icons/logo-mariposa.png";
import fossilBackground from "/fondo-fosiles.png";
import fossilSecondBackground from "/fondo-fosiles-siluetas.png";
import fossilLogo from "/icons/logo-fosil.png";
import artBackground from "/fondo-arte.png";
import artSecondBackground from "/fondo-secundario-arte.png";
import artLogo from "/icons/logo-pintura.png";

interface decorationConfig {
    mainBackground: string;
    secondaryBackground: string;
    title: string;
    subtitle: string;
    logo: string;
    apiType: string;
}

const tematicConfig: Record<string, decorationConfig> = {
    fish: {
        mainBackground: fishBackground,
        secondaryBackground: fishSecondBackground,
        title: "Peces y criaturas marinas",
        subtitle: "En esta sección, podrás sumergirte en el fascinante mundo de los peces. Explora su diversidad, colores y formas únicas en esta sección dedicada a las maravillas acuáticas.",
        logo: fishLogo,
        apiType: "peces"
    },
    insects: {
        mainBackground: insectBackground,
        secondaryBackground: insectSecondBackground,
        title: "Insectos",
        subtitle: "Adéntrate en el mundo de los insectos, criaturas asombrosas que habitan nuestro planeta. Desde coloridas mariposas hasta fascinantes escarabajos, esta sección te invita a descubrir la diversidad y belleza de estos pequeños seres.",
        logo: insectLogo,
        apiType: "insectos"
    },
    fossils: {
        mainBackground: fossilBackground,
        secondaryBackground: fossilSecondBackground,
        title: "Fósiles y minerales",
        subtitle: "Los fósiles son restos o impresiones de organismos que vivieron en el pasado, mientras que los minerales son sustancias sólidas inorgánicas con una composición química definida. En esta sección, podrás explorar la historia de la Tierra a través de sus fósiles y descubrir la belleza de los minerales que la componen.",
        logo: fossilLogo,
        apiType: "fosiles"
    },
    art: {
        mainBackground: artBackground,
        secondaryBackground: artSecondBackground,
        title: "Obras de arte",
        subtitle: "En esta sección, podrás admirar una colección de obras de arte que abarcan diferentes estilos y epochs. Desde pinturas clásicas hasta esculturas contemporáneas, esta sección te invita a explorar la creatividad humana a través de sus expresiones artísticas.",
        logo: artLogo,
        apiType: "arte"
    }
};


export async function clientLoader({ params }: Route.ClientLoaderArgs) {
    const urlType = params.type || "fish";
    const sectionConfig = tematicConfig[urlType] || tematicConfig.fish;

    try {
        const result = await getMuseumObjects(0, { type: sectionConfig.apiType });
        return { items: result.items, hasNext: result.hasNext };
    } catch (error) {
        console.error("Error cargando la sección:", error);
        return { items: [], hasNext: false };
    }
}

export default function SectionPage({ loaderData }: Route.ComponentProps) {
    const { type } = useParams<{ type: string }>();
    const { user } = useUserStore();
    const navigate = useNavigate();

    const section = tematicConfig[type || "fish"] || tematicConfig.fish;

    const inicialItems = loaderData?.items ? loaderData.items : [];
    const inicialHasMore = loaderData?.hasNext ? loaderData.hasNext : false;

    const [objects, setObjects] = useState<MuseumObjectBasicDTO[]>(inicialItems);
    const [currentPage, setCurrentPage] = useState(0);
    const [hasMore, setHasMore] = useState(inicialHasMore);
    const [loading, setLoading] = useState(false);

    const [activeCategory, setActiveCategory] = useState<string | null>(null);
    const [activeSearchQuery, setActiveSearchQuery] = useState<string | null>(null);

    useEffect(() => {
        setObjects(inicialItems);
        setCurrentPage(0);
        setHasMore(inicialHasMore);
        setActiveCategory(null);
        setActiveSearchQuery(null);
    }, [type, loaderData]);

    const filteredObjects = objects;

    const handleDelete = async (id: number) => {

        try {
            await deleteMuseumObject(id);
            setObjects((prevObjects) => prevObjects.filter((obj) => obj.id !== id));
            const message = "Objeto eliminado correctamente.";
            const encodeMessage = encodeURIComponent(message);
            navigate(`/notification?type=confirmation&message=${encodeMessage}`);
        } catch (error) {
            const message = "Se ha producido un error al eliminar el objeto.";
            const encodeMessage = encodeURIComponent(message);
            navigate(`/notification?type=error&message=${encodeMessage}`);
        }
    };

    const loadMoreObjects = async () => {
        setLoading(true);
        const nextPage = currentPage + 1;
        const result = await getMuseumObjects(nextPage, {
            type: section.apiType,
            category: activeCategory || undefined,
            name: activeSearchQuery || undefined,
        });

        if (result?.items) {
            setObjects([...objects, ...result.items]);
            setHasMore(result.hasNext);
        }
        setCurrentPage(nextPage);
        setLoading(false);
    };

    const handleSearch = async (query: string) => {
        setLoading(true);
        setCurrentPage(0);
        setActiveCategory(null); 
        setActiveSearchQuery(query);

        try {
            const result = await getMuseumObjects(0, {
                type: section.apiType,
                name: query || undefined
            });
            setObjects(result.items);
            setHasMore(result.hasNext);
        } catch (error) {
            console.error("Error en la búsqueda:", error);
            setObjects([]);
            setHasMore(false);
        }
        setLoading(false);
    };

    const handleFilterCategory = async (category: string) => {
        setLoading(true);
        setCurrentPage(0);
        setActiveSearchQuery(null); 
        
        const nextCategory = activeCategory === category ? null : category;
        setActiveCategory(nextCategory);

        try {
            const result = await getMuseumObjects(0, {
                type: section.apiType,
                category: nextCategory || undefined
            });
            setObjects(result.items);
            setHasMore(result.hasNext);
        } catch (error) {
            console.error("Error al filtrar por categoría:", error);
            setObjects([]);
            setHasMore(false);
        }
        setLoading(false);
    };

    return (
        <>
            <div className="py-4 text-white" style={{
                backgroundImage: `url(${section.mainBackground})`,
                backgroundSize: "cover",
                backgroundPosition: "center",
                backgroundRepeat: "no-repeat",
                minHeight: "60vh",
            }}>
                <Container className="px-4 px-lg-5 my-5">
                    <div className="text-center text-white">
                        <div className="container h-100 d-flex flex-row align-items-center justify-content-center">
                            <img src={section.logo} className="section-logo me-3 rounded-circle me-4" alt="logo" />
                            <h1 className="display-4 fw-bolder">{section.title}</h1>
                        </div>
                        <div className="my-5">
                            <h4 className="text-center text-white">
                                {section.subtitle}
                            </h4>
                        </div>
                    </div>
                </Container>
            </div>

            <SearchBar 
                currentType={type || "fish"} 
                onSearch={handleSearch} 
                onFilter={handleFilterCategory} 
            />

            <div className="py-4 text-white" style={{
                backgroundImage: `url(${section.secondaryBackground})`,
                backgroundSize: "cover",
                backgroundPosition: "center",
                backgroundRepeat: "no-repeat",
                minHeight: "75vh",
            }}>
                <Container>
                    {filteredObjects.length > 0 ? (
                        <Row xs={1} md={2} lg={4} className="g-4">
                            {filteredObjects.map((object: MuseumObjectBasicDTO) => (
                                <Col key={object.id}>
                                    <ObjectCard
                                        object={object}
                                        isMenu={false}
                                        isAdmin={user?.roles?.includes("ADMIN")}
                                        menuCategory={[]}
                                        onAccess={() => navigate(`/objects/${type}/${object.id}`)}
                                        onEliminate={(id) => handleDelete(Number(id))}
                                    />
                                </Col>
                            ))}
                        </Row>
                    ) : (
                        <div className="text-center py-5 bg-dark bg-opacity-75 rounded-3 shadow-sm border border-secondary my-4">
                            <h3 className="fw-bold text-warning mb-2">No se encontraron objetos</h3>
                            <p className="text-light opacity-75 mb-0 mx-auto px-3" style={{ maxWidth: "550px" }}>
                                No hay registros que coincidan con tu criterio de búsqueda en la colección de {section.title.toLowerCase()}. 
                                Prueba a limpiar los filtros o realizar una consulta diferente.
                            </p>
                        </div>
                    )}


                    {hasMore && filteredObjects.length > 0 && (
                        <div className="text-center mt-5">
                            <Button variant="secondary" onClick={loadMoreObjects} disabled={loading} className="d-inline-flex align-items-center fw-bold text-white px-4 py-2 shadow-sm rounded-pill">
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

                    {user?.roles?.includes("ADMIN") && (
                        <div className="mt-5 text-center border-top pt-4">
                            <Button as={Link as any} to={`/new-object/${type}`} variant="success" 
                            className="d-inline-flex align-items-center fw-bold shadow-sm text-white px-3 py-2 rounded-3">
                                <Plus className="me-2" />
                                Añadir
                            </Button>
                        </div>
                    )}
                </Container>
            </div>
        </>
    );
}