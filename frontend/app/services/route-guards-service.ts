import type {UserDTO} from "~/dtos/UserDTO";
import {reqIsLogged} from "~/services/login-service";


export async function requiredLoggedUser(): Promise<UserDTO> {

    try {
        return await reqIsLogged();
    } catch (error) {
        throw new Response("No estás autorizado para acceder a esta página.", {status: 401});
    }
}


export async function requiredOnlyStandardUser(): Promise<UserDTO> {
    const user = await requiredLoggedUser();
    const isAdmin = user.roles?.includes("ADMIN");
    const isUser = user.roles?.includes("USER");

    if (isAdmin || !isUser) {
        throw new Response("No tienes permiso para acceder a esta página.", {status: 403});
    }

    return user;
}


export async function requiredAdmin(): Promise<UserDTO> {
    const user = await requiredLoggedUser();
    const isAdmin = user.roles?.includes("ADMIN");

    if (!isAdmin) {
        throw new Response("No tienes permiso para acceder a esta página.", {status: 403});
    }

    return user;
}


export async function requiredAnonymousUser(): Promise<void> {

    const user = await requiredLoggedUser();

    if (user) {
        throw new Response("No tienes permiso para acceder a esta página.", {status: 403});
    }

    return;
}


export function checkPermission(currentUser: UserDTO, ownerId?: number): void {
  const isAdmin = currentUser.roles?.includes("ADMIN");

  const isOwner = ownerId !== undefined && currentUser.id === ownerId;

  if (!isAdmin && !isOwner) {
    throw new Response("No tienes permiso para acceder a esta página.", { status: 403 });
  }
}



export function checkPermissionAlternative(currentUser: UserDTO, ownerId?: number): void {
  const isAdmin = currentUser.roles?.includes("ADMIN");

  const isOwner = ownerId !== undefined && currentUser.id === ownerId;

  if (isAdmin && !isOwner) {
    throw new Response("No tienes permiso para acceder a esta página.", { status: 403 });
  }
}