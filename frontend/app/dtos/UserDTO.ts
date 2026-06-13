import type {ImageDTO} from "./ImageDTO";

export interface UserDTO {
    id: number;
    name: string;
    password: string;
    roles: string[];
    userImage?: ImageDTO;
}