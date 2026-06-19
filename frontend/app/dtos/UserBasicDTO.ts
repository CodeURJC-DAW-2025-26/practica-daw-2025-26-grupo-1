import type { ElementDTO } from "./ElemetDTO";
import type {ImageDTO} from "./ImageDTO";

export interface UserBasicDTO {
    id: number;
    name: string;
    roles: string[];
    seen: ElementDTO[];
    userImage?: ImageDTO;
}