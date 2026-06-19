import type { ElementDTO } from "./ElemetDTO";
import type {ImageDTO} from "./ImageDTO";

export interface UserDTO {
    id: number;
    name: string;
    password: string;
    roles: string[];
    seen: ElementDTO[];
    userImage?: ImageDTO;
}