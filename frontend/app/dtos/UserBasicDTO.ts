import type {ImageDTO} from "./ImageDTO";

export interface UserBasicDTO {
    id: number;
    name: string;
    roles: string[];
    userImage?: ImageDTO;
}