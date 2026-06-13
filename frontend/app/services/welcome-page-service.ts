
import type SectionDTO from "~/dtos/SectionDTO";

const API_SECTIONS_URL = "/api/v1/sections";

export async function getSections(): Promise<SectionDTO[]> {
    const response = await fetch(`${API_SECTIONS_URL}/`);

    if (!response.ok) {
    throw new Error("No se han podido obtener las secciones del museo.");
  }

  return await response.json();
}