"use client";

import { Piece } from "@/lib/data";
import { useCart } from "@/lib/cart-context";
import { ShoppingBag } from "lucide-react";
import Image from "next/image";
import { useState } from "react";

type Props = {
  title: string;
  accent: "red" | "gold";
  pieces: Piece[];
  sectionId?: string;
};

function AddToCartButton({ piece, accent }: { piece: Piece; accent: "red" | "gold" }) {
  const { addItem } = useCart();
  const [added, setAdded] = useState(false);

  function handleAdd() {
    addItem({
      slug: piece.slug,
      name: piece.name,
      image: piece.image,
      price: piece.price,
      priceKobo: piece.priceKobo,
    });
    setAdded(true);
    setTimeout(() => setAdded(false), 1200);
  }

  const bg = accent === "red"
    ? "bg-brand-red hover:bg-red-500"
    : "bg-brand-gold hover:bg-yellow-600";

  return (
    <button
      onClick={handleAdd}
      className={`mt-3 flex h-11 w-full items-center justify-center gap-2 rounded-full text-xs font-semibold uppercase tracking-wider text-white transition ${bg} active:scale-95`}
    >
      {added ? (
        "Added!"
      ) : (
        <>
          <ShoppingBag size={14} />
          Add to Cart
        </>
      )}
    </button>
  );
}

export default function CollectionGrid({ title, accent, pieces, sectionId }: Props) {
  const accentClass = accent === "red" ? "text-brand-red" : "text-brand-gold";
  const words = title.split(" ");
  const lastWord = words[words.length - 1];
  const firstWords = words.slice(0, -1).join(" ");

  return (
    <section className="mx-auto max-w-6xl px-4 py-14 md:px-8" id={sectionId}>
      <h2 className="display-font text-5xl md:text-6xl">
        {firstWords} <span className={accentClass}>{lastWord}</span>
      </h2>
      <div className="mt-8 grid gap-6 sm:grid-cols-2 lg:grid-cols-4">
        {pieces.map((piece) => (
          <article
            key={piece.slug}
            className="flex flex-col overflow-hidden rounded-2xl border border-white/10 bg-zinc-950/70"
          >
            <div className="relative h-72">
              <Image src={piece.image} alt={piece.name} fill sizes="(max-width: 640px) 100vw, (max-width: 1024px) 50vw, 25vw" className="object-cover transition duration-500 hover:scale-105" />
            </div>
            <div className="flex flex-1 flex-col p-4">
              <h3 className="display-font text-2xl leading-none text-white">{piece.name}</h3>
              <p className="mt-2 text-sm text-zinc-300">{piece.subtitle}</p>
              <p className={`mt-3 text-lg font-bold ${accentClass}`}>{piece.price}</p>
              <div className="mt-auto">
                <AddToCartButton piece={piece} accent={accent} />
              </div>
            </div>
          </article>
        ))}
      </div>
    </section>
  );
}
