import content from "@/content.json";

export type Piece = {
  slug: string;
  name: string;
  subtitle: string;
  price: string;
  priceKobo: number;
  image: string;
};

export type CollectionConfig = {
  title: string;
  accent: "red" | "gold";
  pieces: Piece[];
};

export const siteContent = content;

export const srntzCollection: CollectionConfig = {
  ...content.collections.srntz,
  accent: content.collections.srntz.accent as "red" | "gold",
};

export const lndCollection: CollectionConfig = {
  ...content.collections.lnd,
  accent: content.collections.lnd.accent as "red" | "gold",
};

export const lookbookImages = content.lookbook;
