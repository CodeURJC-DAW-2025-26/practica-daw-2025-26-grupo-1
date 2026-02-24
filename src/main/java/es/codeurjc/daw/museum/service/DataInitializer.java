package es.codeurjc.daw.museum.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.Image;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.repository.MuseumObjectRepository;
import es.codeurjc.daw.museum.repository.UserRepository;

@Service
public class DataInitializer {

	/*@Autowired
	private BookService bookService;

	@Autowired
	private ShopService shopService;	*/

	@Autowired
	private ImageService imageService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MuseumObjectRepository objectRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() throws IOException, URISyntaxException {

		// Objetos de la sección Peces

		MuseumObject obj1 = new MuseumObject();
		obj1.setObjectName("Pez napoleón");
		obj1.setGroupName("Bolbometopon muricatum");
		obj1.setType("Peces");
		obj1.setCategory("Mar");
		/*obj1.setImage(obj1, "/proyect-images/fish/sea/pez-napoleon.png");
		obj1.setTechnicalData("Nombre científico: Bolbometopon muricatum\nGrupo: Orden Labriformes
Longitud: Hasta 2,3 m
Peso: Hasta 190 kg
Esperanza de vida: Hasta 30 años
Hábitat: Arrecifes de coral tropicales del Indo-Pacífico
Dieta: Herbívoro, consume algas y corales duros
Reproducción: Ovíparo
Antigüedad: Especie moderna
Dato destacado: Es el pez más grande de la familia Labridae y puede cambiar de sexo a lo largo de su vida
Estado de conservación: Vulnerable");*/
		
		/*obj1.setDescription("El pez napoleón, científicamente conocido como Bolbometopon muricatum, es uno de los peces más emblemáticos y grandes de los arrecifes de coral tropicales del Indo-Pacífico. Su característica más notable es la protuberancia ósea en la frente, que se asemeja a un casco o joroba, y que aumenta de tamaño a medida que el pez madura. Esta peculiaridad le ha dado su nombre común, inspirado en el famoso sombrero de Napoleón Bonaparte.

Este pez puede llegar a medir hasta 2.3 metros y pesar alrededor de 190 kilogramos, lo que lo convierte en el miembro más grande de la familia Labridae. Su cuerpo robusto está cubierto por escamas grandes de tonos verdes y azules, con patrones irregulares que varían entre individuos, facilitando su camuflaje entre los corales.

El pez napoleón es principalmente herbívoro, alimentándose de algas y corales duros, que raspa con sus fuertes dientes frontales. Este hábito alimenticio es crucial para la salud de los arrecifes, ya que ayuda a controlar el crecimiento excesivo de algas, permitiendo que los corales puedan prosperar. Curiosamente, posee una dentadura capaz de triturar el coral, contribuyendo también a la formación de arena coralina.

En cuanto a su reproducción, el pez napoleón es ovíparo y hermafrodita secuencial, es decir, algunos individuos cambian de sexo de hembra a macho a lo largo de su vida. Este fenómeno contribuye a la dinámica social y reproductiva del grupo. Durante la época de reproducción, suelen reunirse en grandes agregaciones, facilitando el encuentro entre machos y hembras.

Aunque antes era abundante, el pez napoleón enfrenta amenazas debido a la pesca excesiva, especialmente para la alimentación y el comercio de acuarios, y a la pérdida y degradación de su hábitat por el daño a los arrecifes de coral. Debido a estos factores, la Unión Internacional para la Conservación de la Naturaleza (UICN) lo clasifica actualmente como vulnerable. La conservación del pez napoleón es vital no solo por su valor ecológico, sino también como indicador de la salud general de los ecosistemas coralinos en los que vive.");
		*/objectRepository.save(obj1);


		MuseumObject obj2 = new MuseumObject();
		obj2.setObjectName("Koi");
		obj2.setGroupName("Cyprinus rubrofuscus (variedad ornamental)");
		obj2.setType("Peces");
		obj2.setCategory("Agua dulce");
		/*obj2.setImage(obj2, "/proyect-images/fish/freshwater/koi.png");
		obj2.setTechnicalData("Nombre científico: Cyprinus rubrofuscus (variedad ornamental)
Grupo: Orden Cypriniformes
Longitud: Hasta 90 cm
Peso: Hasta 15 kg
Esperanza de vida: 25–35 años, algunos ejemplares más de 50 años
Hábitat: Ríos y estanques de Asia, adaptado a estanques artificiales en todo el mundo
Dieta: Omnívoro; plantas, insectos, crustáceos y alimentos comerciales para koi
Reproducción: Ovíparo
Antigüedad: Variedad desarrollada hace más de 200 años a partir de la carpa común
Dato destacado: Símbolo de perseverancia y buena suerte en la cultura japonesa
Estado de conservación: No amenazado (especie domesticada)");
		
		obj2.setDescription("El koi es una variedad ornamental de la carpa común (Cyprinus rubrofuscus), criada selectivamente durante siglos por su belleza, colores vivos y patrones únicos. Originario de Asia, especialmente de Japón y China, el koi es hoy un pez emblemático en estanques decorativos y jardines acuáticos en todo el mundo.

Los koi pueden alcanzar tamaños impresionantes de hasta 90 centímetros y pesar más de 15 kilogramos en condiciones ideales. Su cuerpo robusto y escamoso presenta una gran variedad de colores y diseños, que van desde tonos rojos, blancos, negros, amarillos y azules, hasta combinaciones complejas que los hacen piezas únicas para los aficionados.

Además de su atractivo visual, el koi es valorado culturalmente, especialmente en Japón, donde simboliza la perseverancia, la fuerza y la buena suerte. Su presencia en estanques y jardines está vinculada a la armonía y la prosperidad.

Desde el punto de vista biológico, los koi son omnívoros y adaptables, capaces de alimentarse tanto de materia vegetal como animal. Son peces resistentes que se adaptan bien a diferentes condiciones de agua, aunque requieren cuidados específicos para mantener su salud y coloración.

En cuanto a la reproducción, los koi son ovíparos y se reproducen fácilmente en estanques, aunque las crías requieren un manejo cuidadoso para evitar la endogamia y mantener las características deseadas.

Aunque no están en peligro de extinción, como especie domesticada, los koi dependen de la cría en cautiverio para su mantenimiento y desarrollo, y son muy apreciados tanto en la acuicultura ornamental como en la cultura popular.");
		objectRepository.save(obj2);


		MuseumObject obj3 = new MuseumObject();
		obj3.setObjectName("Pez cabeza transparente");
		obj3.setGroupName("Macropinna microstoma");
		obj3.setType("Peces");
		obj3.setCategory("Abisales");
		obj3.setImage(obj3, "/proyect-images/fish/deep-sea/pez-cabeza-transparente.png");
		obj3.setTechnicalData("Nombre científico: Macropinna microstoma
Grupo: Orden Opisthoproctiformes
Longitud: Hasta 15 cm
Peso: Aproximadamente 20 g
Esperanza de vida: Desconocida (estimada en varios años)
Hábitat: Aguas profundas del Pacífico, entre 600 y 800 m
Dieta: Pequeños crustáceos y zooplancton
Reproducción: Ovíparo
Antigüedad: Especie moderna
Dato destacado: Su cabeza es completamente transparente, permitiendo ver ojos y órganos internos
Estado de conservación: No evaluado");
		
		obj3.setDescription("El pez cabeza transparente, Macropinna microstoma, es una fascinante especie marina que habita en las oscuras profundidades del océano Pacífico, usualmente a profundidades que oscilan entre los 600 y 800 metros. Lo que hace realmente único a este pez es su cráneo translúcido, una “ventana” transparente que cubre su cabeza y permite observar directamente sus órganos y, especialmente, sus ojos tubulares hacia adelante.

Esta adaptación tan particular le permite a sus ojos, que pueden rotar dentro de la cabeza, buscar presas en la oscuridad con mayor precisión, una ventaja vital en su hábitat profundo donde la luz solar no llega. Los ojos están protegidos dentro de este “casco” transparente que minimiza la exposición a partículas en suspensión y posibles daños.

El pez cabeza transparente alcanza una longitud máxima de unos 15 centímetros y pesa muy poco, cerca de 20 gramos. Su dieta consiste principalmente en pequeños crustáceos y zooplancton que captura nadando lentamente y con movimientos delicados, aprovechando su excelente visión.

Aunque poco se sabe sobre su reproducción, se cree que es ovíparo, como la mayoría de peces marinos profundos. Su ciclo de vida y longevidad son aún temas de investigación debido a lo inaccesible de su hábitat.

No está evaluado formalmente por la UICN, ya que es una especie poco conocida y difícil de estudiar. Sin embargo, su particular morfología y comportamiento la convierten en un ícono de la biodiversidad adaptativa de las zonas abisales oceánicas.");
		objectRepository.save(obj3);


		MuseumObject obj4 = new MuseumObject();
		obj4.setObjectName("Tetra neón");
		obj4.setGroupName("Paracheirodon innesi");
		obj4.setType("Peces");
		obj4.setCategory("Agua dulce");
		obj4.setImage(obj4, "/proyect-images/fish/freshwater/tetra-neon.png");
		obj4.setTechnicalData("Nombre científico: Paracheirodon innesi
Grupo: Orden Characiformes
Longitud: 3–4 cm
Peso: Menos de 1 g
Esperanza de vida: 5–10 años
Hábitat: Ríos y arroyos de América del Sur, especialmente cuenca del Amazonas
Dieta: Omnívoro; pequeños insectos, larvas, plancton y alimentos comerciales
Reproducción: Ovíparo, desove en grupo sobre plantas finas
Antigüedad: Especie moderna, descubierta a principios del siglo XX
Dato destacado: Sus colores azul y rojo brillante lo hacen muy popular en acuarios
Estado de conservación: No amenazado");
		
		obj4.setDescription("El tetra neón, Paracheirodon innesi, es un pequeño pez de agua dulce originario de América del Sur, muy apreciado por su brillante coloración y su comportamiento pacífico en acuarios comunitarios. Su cuerpo es translúcido con una franja azul metálica que recorre longitudinalmente desde la cabeza hasta la cola y una franja roja en la mitad posterior, lo que le da un efecto visual llamativo, especialmente en grupos numerosos nadando juntos.

Este pez alcanza apenas de 3 a 4 centímetros de longitud y tiene un peso muy bajo, menos de un gramo, pero su belleza lo hace extremadamente popular en acuarios domésticos de todo el mundo. Vive en aguas cálidas, claras o ligeramente oscuras por la presencia de hojas y turba, y prefiere ambientes con corriente suave y abundante vegetación, que le proporcionan refugio y zonas de desove.

El tetra neón es omnívoro y se alimenta de pequeños insectos, larvas, plancton y alimentos balanceados de acuario. Su reproducción es ovípara: las hembras depositan huevos sobre plantas finas o en la grava, y se recomienda retirar a los adultos después del desove, ya que pueden comerse los huevos.

A pesar de su popularidad en acuarios, el tetra neón depende de la conservación de sus hábitats naturales. La deforestación y la contaminación de ríos amazónicos pueden afectar algunas poblaciones locales, aunque la especie en general no está amenazada.

Su comportamiento social y colores brillantes lo convierten en un símbolo de armonía y belleza en acuarios, siendo una de las especies más emblemáticas para principiantes y aficionados avanzados.");
		objectRepository.save(obj4);


		MuseumObject obj5 = new MuseumObject();
		obj5.setObjectName("Celacanto");
		obj5.setGroupName("Coelacanthiformes (Coelacanthimorpha)");
		obj5.setType("Peces");
		obj5.setCategory("Abisales");
		obj5.setImage(obj5, "/proyect-images/fish/deep-sea/celacanto.png");
		obj5.setTechnicalData("Nombre científico: Latimeria chalumnae
Grupo: Orden Coelacanthiformes
Longitud: Hasta 2 m
Peso: Hasta 90 kg
Esperanza de vida: Más de 60 años
Hábitat: Aguas profundas (150–700 m), cuevas submarinas del Océano Índico occidental e Indonesia
Dieta: Peces pequeños y cefalópodos
Reproducción: Vivíparo
Antigüedad: Linaje de más de 400 millones de años
Dato destacado: Se creía extinto hasta su redescubrimiento en 1938
Estado de conservación: En peligro crítico");
		
		obj5.setDescription("El celacanto es un pez marino prehistórico perteneciente al género Latimeria. Durante millones de años solo se conocía por fósiles y se creía extinguido desde el final de la era de los dinosaurios, hasta que en 1938 fue redescubierto un ejemplar vivo en Sudáfrica. Este hallazgo fue uno de los más importantes del siglo XX en el ámbito científico.

Su linaje se remonta a hace aproximadamente 400 millones de años, lo que lo convierte en uno de los grupos de vertebrados más antiguos que aún existen. Es conocido como “fósil viviente” porque su morfología ha cambiado muy poco a lo largo del tiempo geológico.

Puede alcanzar hasta 2 metros de longitud y pesar alrededor de 90 kg. Su cuerpo es robusto, de color azul grisáceo con manchas blancas. Vive en aguas profundas (entre 150 y 700 metros), generalmente en cuevas volcánicas submarinas, donde permanece durante el día y sale por la noche para alimentarse de peces pequeños y cefalópodos.

Una de sus características más destacadas son sus aletas lobuladas y carnosas, que presentan una estructura ósea similar a la de las extremidades de los primeros vertebrados terrestres. También posee un órgano especial en la parte frontal de la cabeza, llamado órgano rostral, que le permite detectar señales eléctricas en el agua.

Actualmente existen dos especies vivas: Latimeria chalumnae, localizada en el Océano Índico occidental, y Latimeria menadoensis, en Indonesia. Su reproducción es vivípara y su crecimiento es lento; puede vivir más de 60 años.

Debido a su escasa población y a la pesca accidental, está catalogado como En Peligro Crítico según la UICN, lo que lo convierte en una especie de gran interés científico y de especial importancia para la conservación marina.");
		objectRepository.save(obj5);


		MuseumObject obj6 = new MuseumObject();
		obj6.setObjectName("Pez cirujano");
		obj6.setGroupName("Acanthurus (varias especies)");
		obj6.setType("Peces");
		obj6.setCategory("Mar");
		obj6.setImage(obj6, "/proyect-images/fish/sea/pez-cirujano.png");
		obj6.setTechnicalData("Nombre científico: Acanthurus (género, varias especies)
Grupo: Orden Perciformes
Longitud: Hasta 40 cm (dependiendo de la especie)
Peso: Hasta 3 kg aproximadamente
Esperanza de vida: 10–20 años
Hábitat: Arrecifes de coral tropicales del Indo-Pacífico y Atlántico
Dieta: Herbívoro principalmente; consume algas y detritos, algunas especies son omnívoras
Reproducción: Ovíparo
Antigüedad: Especie moderna
Dato destacado: Posee “cuchillas” afiladas en la base de la cola que utiliza para defenderse
Estado de conservación: Mayormente no amenazado, aunque algunas especies locales pueden estar afectadas por la pesca y degradación de arrecifes");
		
		obj6.setDescription("El pez cirujano es un grupo de peces marinos que habitan los arrecifes de coral tropicales, principalmente en el Indo-Pacífico y algunas zonas del Atlántico. Se caracterizan por su cuerpo ovalado y comprimido lateralmente, con colores brillantes que varían entre azul, amarillo, naranja y combinaciones de estos, dependiendo de la especie.

Su nombre proviene de las afiladas “cuchillas” situadas en la base de la cola, que funcionan como defensa frente a depredadores y les permiten protegerse eficazmente. Estas cuchillas recuerdan un bisturí, de ahí el nombre de “cirujano”.

El pez cirujano puede alcanzar hasta 40 centímetros de longitud y pesar alrededor de 3 kilogramos. Su dieta es principalmente herbívora: se alimenta de algas que crecen sobre los corales, contribuyendo a mantener el equilibrio ecológico del arrecife y evitando que las algas compitan con los corales por el espacio. Algunas especies también consumen pequeños invertebrados y detritos.

Su reproducción es ovípara, y suelen formar bancos grandes que nadan juntos, lo que les proporciona protección frente a los depredadores. La esperanza de vida varía entre 10 y 20 años, dependiendo de la especie y las condiciones del hábitat.

Aunque la mayoría de las especies de pez cirujano no están amenazadas, la degradación de los arrecifes y la pesca ornamental pueden afectar algunas poblaciones locales. Son peces de gran importancia ecológica, ya que ayudan a conservar la salud y biodiversidad de los arrecifes de coral.");
		objectRepository.save(obj6);


		// Objetos de la sección Insectos


		MuseumObject obj7 = new MuseumObject();
		obj7.setObjectName("Escorpión");
		obj7.setGroupName("Orden Scorpiones");
		obj7.setType("Insectos");
		obj7.setCategory("Terrestres");
		obj7.setImage(obj7, "/proyect-images/insects/terrestrial/escorpion.png");
		obj7.setTechnicalData("Nombre científico: Orden Scorpiones
Grupo: Clase Arachnida
Longitud: 1–20 cm, dependiendo de la especie
Peso: 1–30 g aproximadamente
Esperanza de vida: 3–8 años, algunas especies hasta 25 años
Hábitat: Desiertos, selvas, cuevas y zonas rocosas en todo el mundo (excepto la Antártida)
Dieta: Carnívoro; insectos, arácnidos más pequeños y otros invertebrados
Reproducción: Vivíparo; las crías nacen vivas y se mantienen sobre la madre al principio
Antigüedad: Presente desde hace más de 430 millones de años
Dato destacado: Posee un aguijón venenoso en la cola para cazar y defenderse
Estado de conservación: Mayoría de especies no amenazadas; algunas locales pueden estar en riesgo");
		
		obj7.setDescription("El escorpión es un artrópodo perteneciente a la clase Arachnida, caracterizado por su cuerpo segmentado, pinzas delanteras (pedipalpos) y una cola curvada con un aguijón venenoso en el extremo. Su linaje es muy antiguo, presente en la Tierra desde hace más de 430 millones de años, lo que lo convierte en uno de los artrópodos más antiguos que aún existen.

Dependiendo de la especie, el escorpión puede medir desde 1 hasta 20 centímetros. Es un depredador nocturno que se alimenta de insectos, arácnidos más pequeños y otros invertebrados, utilizando sus pinzas para sujetar a la presa y su aguijón para inyectar veneno y paralizarla.

La reproducción de los escorpiones es vivípara; las crías nacen vivas y permanecen sobre la madre durante las primeras semanas de vida hasta que pueden valerse por sí mismas. Su esperanza de vida varía según la especie, pero puede oscilar entre 3 y 8 años, alcanzando incluso hasta 25 años en algunas especies de gran tamaño.

A pesar de que algunas especies poseen un veneno potente, la mayoría no representa un peligro grave para los humanos. Los escorpiones juegan un papel importante en los ecosistemas como controladores de poblaciones de insectos y otros pequeños invertebrados.");
		objectRepository.save(obj7);


		MuseumObject obj8 = new MuseumObject();
		obj8.setObjectName("Mariposa Atlas");
		obj8.setGroupName("Attacus atlas");
		obj8.setType("Insectos");
		obj8.setCategory("Aéreos");
		obj8.setImage(obj8, "/proyect-images/insects/flyers/mariposa-atlas.png");
		obj8.setTechnicalData("Nombre científico: Attacus atlas
Grupo: Orden Lepidoptera
Envergadura: Hasta 25–30 cm, una de las más grandes del mundo
Peso: Aproximadamente 12–20 g
Esperanza de vida: 1–2 semanas (adulto)
Hábitat: Bosques tropicales y subtropicales del sudeste asiático, especialmente Indonesia, Malasia y Filipinas
Dieta: La fase adulta no se alimenta; las orugas se alimentan de hojas de árboles y arbustos
Reproducción: Ovíparo; la hembra deposita huevos sobre hojas
Antigüedad: Especie moderna
Dato destacado: Sus alas presentan patrones que imitan cabezas de serpiente para ahuyentar depredadores
Estado de conservación: No amenazada, aunque puede verse afectada por destrucción de hábitats");
		
		obj8.setDescription("La mariposa atlas (Attacus atlas) es una de las mariposas más grandes del mundo, reconocida por su impresionante envergadura que puede superar los 25 centímetros. Su nombre proviene del titán Atlas de la mitología griega, reflejando su tamaño y majestuosidad.

Es nativa de los bosques tropicales y subtropicales del sudeste asiático, especialmente en Indonesia, Malasia y Filipinas. Las alas de la mariposa atlas presentan colores marrones, naranjas y rojos, con patrones que se asemejan a cabezas de serpiente en las puntas, un mecanismo de defensa que ayuda a ahuyentar a depredadores.

El adulto no se alimenta, ya que su aparato digestivo está reducido; toda su energía proviene de lo que acumuló durante la fase de oruga. Las orugas, en cambio, se alimentan de hojas de árboles y arbustos, creciendo rápidamente hasta alcanzar el tamaño necesario para la metamorfosis.

La reproducción es ovípara; la hembra deposita los huevos sobre hojas seleccionadas, asegurando alimento para las futuras larvas. La vida adulta es breve, generalmente entre 1 y 2 semanas, periodo en el cual el único objetivo es reproducirse.

Aunque no está considerada una especie amenazada, la mariposa atlas puede verse afectada por la pérdida de hábitat debido a la deforestación y la urbanización. Su tamaño y belleza la convierten en un ícono de los insectos tropicales y un ejemplo fascinante de adaptación evolutiva.");
		objectRepository.save(obj8);


		MuseumObject obj9 = new MuseumObject();
		obj9.setObjectName("Zapatero");
		obj9.setGroupName("Familia Gerridae");
		obj9.setType("Insectos");
		obj9.setCategory("Acuáticos");
		obj9.setImage(obj9, "/proyect-images/insects/acuatic/zapatero.png");
		obj9.setTechnicalData("Nombre científico: Familia Gerridae
Grupo: Orden Hemiptera
Longitud: 1–2,5 cm, dependiendo de la especie
Peso: Muy ligero, apenas unos miligramos
Esperanza de vida: 1 año aproximadamente
Hábitat: Superficies de agua dulce como lagos, ríos, estanques y charcas en casi todo el mundo
Dieta: Carnívoro; pequeños insectos y organismos que caen sobre la superficie del agua
Reproducción: Ovíparo; deposita huevos en plantas acuáticas o en la superficie del agua
Antigüedad: Especie moderna
Dato destacado: Puede caminar sobre el agua gracias a la tensión superficial y sus patas hidrófobas
Estado de conservación: No amenazado");
		
		obj9.setDescription("El zapatero acuático pertenece a la familia Gerridae y es un insecto adaptado a desplazarse sobre la superficie del agua. Su nombre común se debe a que parece “caminar” sobre el agua como si fueran zapatos, aprovechando la tensión superficial y las propiedades hidrófobas de sus patas largas y finas.

Dependiendo de la especie, el zapatero mide entre 1 y 2,5 centímetros y tiene un cuerpo ligero que le permite distribuir su peso sin hundirse. Se encuentra en aguas dulces tranquilas como ríos, lagos, estanques y charcas, y está presente en casi todo el mundo.

Su alimentación es principalmente carnívora; se alimenta de pequeños insectos y organismos que caen sobre la superficie del agua. La reproducción es ovípara: las hembras depositan los huevos en plantas acuáticas o directamente sobre la superficie, donde las crías nacerán adaptadas a la vida en el agua.

Gracias a su adaptación única, el zapatero acuático es un ejemplo fascinante de cómo los insectos pueden aprovechar la física del agua para moverse y cazar sin sumergirse. Su población es estable y no está considerada amenazada.");
		objectRepository.save(obj9);


		MuseumObject obj10 = new MuseumObject();
		obj10.setObjectName("Mariposa monarca");
		obj10.setGroupName("Danaus plexippus");
		obj10.setType("Insectos");
		obj10.setCategory("Aéreos");
		obj10.setImage(obj10, "/proyect-images/insects/flyers/mariposa-monarca.png");
		obj10.setTechnicalData("Nombre científico: Danaus plexippus
Grupo: Orden Lepidoptera
Envergadura: 8–10 cm
Peso: Aproximadamente 0,5–1 g
Esperanza de vida: Adultos migratorios: 6–8 meses; generaciones reproductoras: 2–6 semanas
Hábitat: Norteamérica, México y algunos lugares de Centroamérica; se encuentra en praderas, campos y jardines
Dieta: Las orugas se alimentan de hojas de algodoncillo (Asclepias); los adultos consumen néctar de flores
Reproducción: Ovíparo; la hembra deposita huevos individuales sobre las hojas de las plantas hospedantes
Antigüedad: Especie moderna
Dato destacado: Conocida por su migración anual de miles de kilómetros hacia los bosques de México y California
Estado de conservación: Vulnerable, principalmente por pérdida de hábitat y uso de pesticidas");
		
		obj10.setDescription("La mariposa monarca (Danaus plexippus) es un lepidóptero emblemático de Norteamérica, famosa por sus largas migraciones que pueden cubrir miles de kilómetros. Su cuerpo y alas presentan un patrón característico de color naranja con líneas negras y manchas blancas, que funciona como advertencia de su toxicidad para los depredadores.

Durante su fase de oruga, se alimenta exclusivamente de hojas de algodoncillo (Asclepias), de las cuales obtiene compuestos químicos que la hacen desagradable o venenosa para aves y otros depredadores. Los adultos, en cambio, se alimentan de néctar de diversas flores, siendo importantes polinizadores en sus ecosistemas.

La mariposa monarca tiene un ciclo de vida que varía según la generación: las generaciones reproductoras viven pocas semanas, mientras que las generaciones migratorias pueden vivir de 6 a 8 meses para completar su viaje hacia los santuarios de hibernación en México y California.

Su migración es considerada uno de los fenómenos naturales más sorprendentes del reino animal. Sin embargo, la mariposa monarca está catalogada como vulnerable debido a la pérdida de hábitat, la deforestación y el uso de pesticidas, lo que hace que su conservación sea fundamental para mantener esta espectacular especie y sus rutas migratorias.");
		objectRepository.save(obj10);


		MuseumObject obj11 = new MuseumObject();
		obj11.setObjectName("Chinche acuática gigante");
		obj11.setGroupName("Familia Belostomatidae");
		obj11.setType("Insectos");
		obj11.setCategory("Acuáticos");
		obj11.setImage(obj11, "/proyect-images/insects/acuatic/chinche-acuatica.png");
		obj11.setTechnicalData("Nombre científico: Familia Belostomatidae
Grupo: Orden Hemiptera
Longitud: 6–12 cm, dependiendo de la especie
Peso: 5–10 g aproximadamente
Esperanza de vida: 1–2 años
Hábitat: Agua dulce: lagos, ríos, estanques y charcas de América, Asia y Australia
Dieta: Carnívoro; peces pequeños, renacuajos, insectos y otros invertebrados acuáticos
Reproducción: Ovíparo; las hembras depositan huevos sobre plantas acuáticas o sobre el dorso del macho en algunas especies
Antigüedad: Especie moderna
Dato destacado: Posee un pico perforador para inyectar enzimas digestivas y succionar la presa; se le llama “gigante” por su tamaño inusual entre insectos acuáticos
Estado de conservación: Mayormente no amenazada, aunque algunas poblaciones locales pueden verse afectadas por contaminación y pérdida de hábitat");
		
		obj11.setDescription("La chinche acuática gigante pertenece a la familia Belostomatidae y es uno de los insectos acuáticos más grandes del mundo. Su cuerpo es plano y robusto, adaptado a la vida en la superficie y dentro del agua, y puede alcanzar entre 6 y 12 centímetros de longitud.

Es un depredador voraz, que se alimenta de peces pequeños, renacuajos, insectos y otros invertebrados acuáticos. Para cazar, utiliza un pico perforador que inyecta enzimas digestivas en la presa, permitiéndole succionar los tejidos ya digeridos. Este mecanismo hace que sea uno de los insectos acuáticos más efectivos y temidos en su ecosistema.

La reproducción es ovípara, y algunas especies presentan cuidado parental; por ejemplo, en ciertas especies los machos cargan los huevos sobre su dorso hasta que eclosionan. Su esperanza de vida varía entre 1 y 2 años.

Aunque no está amenazada a nivel global, la chinche acuática gigante puede verse afectada por la contaminación de ríos y lagos, así como por la destrucción de humedales. Su tamaño y comportamiento la convierten en un ejemplo impresionante de adaptación y depredación en los ecosistemas acuáticos.");
		objectRepository.save(obj11);


		MuseumObject obj12 = new MuseumObject();
		obj12.setObjectName("Escarabajo Goliat");
		obj12.setGroupName("Goliathus goliatus");
		obj12.setType("Insectos");
		obj12.setCategory("Terrestres");
		obj12.setImage(obj12, "/proyect-images/insects/terrestrial/escarabajo-goliat.png");
		obj12.setTechnicalData("Nombre científico: Goliathus goliatus
Grupo: Orden Coleoptera
Longitud: 5–11 cm, dependiendo del sexo y la especie
Peso: 50–100 g, uno de los insectos más pesados del mundo
Esperanza de vida: Adultos: 3–6 meses; larvas: 1–2 años
Hábitat: Selvas tropicales de África occidental y central
Dieta: Las larvas se alimentan de raíces y madera en descomposición; los adultos consumen savia de árboles y frutas maduras
Reproducción: Ovíparo; las hembras depositan huevos en el suelo o en madera en descomposición
Antigüedad: Especie moderna
Dato destacado: Uno de los insectos más grandes y pesados del mundo; fuerte capacidad de vuelo pese a su tamaño
Estado de conservación: No amenazado, pero vulnerable a la destrucción del hábitat y al comercio de insectos ornamentales");
		
		obj12.setDescription("El escarabajo goliat (Goliathus goliatus) es uno de los insectos más grandes y pesados del mundo, con ejemplares que pueden superar los 11 centímetros de longitud y pesar hasta 100 gramos. Su cuerpo es robusto y está recubierto por un caparazón duro con colores que van del negro al marrón y blanco, a veces con patrones distintivos que ayudan a identificar a cada individuo.

Habita las selvas tropicales de África occidental y central, donde las larvas se desarrollan alimentándose de raíces y madera en descomposición. Los adultos se alimentan principalmente de savia de árboles y frutas maduras, siendo más activos durante el día.

La reproducción es ovípara; las hembras depositan sus huevos en el suelo o en madera descompuesta, y las larvas tardan de 1 a 2 años en desarrollarse antes de transformarse en adultos. La vida adulta es relativamente corta, de 3 a 6 meses, centrada en alimentarse y reproducirse.

A pesar de su tamaño y fuerza de vuelo, el escarabajo goliat puede verse afectado por la destrucción del hábitat y por el comercio ilegal de insectos para colecciones. Es un ejemplo fascinante de adaptación y gigantismo en el mundo de los insectos, y un ícono de la biodiversidad africana.");
		objectRepository.save(obj12);
*/

		// Sample users

		userRepository.save(new User("user", passwordEncoder.encode("pass"), List.of("USER")));
		userRepository.save(new User("admin", passwordEncoder.encode("adminpass"), List.of("USER", "ADMIN")));
	}


	public void setMuseumObjectImage(MuseumObject obj, String classpathResource) throws IOException {

    	Resource image = new ClassPathResource(classpathResource);
    	Image createdImage = imageService.createImage(image.getInputStream());
    	obj.setImage(createdImage);
	}
}