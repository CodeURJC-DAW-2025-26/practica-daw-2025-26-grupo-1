import type { ImageDTO } from "./ImageDTO";

export interface ElementDTO {
    id: number;
    nameElement: string;
    objectSectionImage?: ImageDTO;
    category: string;
    goToElement: string;
}