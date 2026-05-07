import CollectionGrid from "@/components/CollectionGrid";
import ComingSoon from "@/components/ComingSoon";
import Footer from "@/components/Footer";
import Hero from "@/components/Hero";
import Lookbook from "@/components/Lookbook";
import MarqueeTicker from "@/components/MarqueeTicker";
import NavBar from "@/components/NavBar";
import PhotoStrip from "@/components/PhotoStrip";
import SignUpForm from "@/components/SignUpForm";
import { lndCollection, lookbookImages, srntzCollection } from "@/lib/data";

const lndImages = lndCollection.pieces.map((p) => p.image);

export default function Home() {
  return (
    <div className="bg-brand-black text-white">
      <NavBar />
      <main>
        <Hero />
        <MarqueeTicker />
        <CollectionGrid
          title={srntzCollection.title}
          accent={srntzCollection.accent}
          pieces={srntzCollection.pieces}
          sectionId="available"
          showCart
        />
        <PhotoStrip
          title={lndCollection.title}
          accent={lndCollection.accent as "red" | "gold"}
          images={lndImages}
        />
        <Lookbook images={lookbookImages} />
        <ComingSoon />
        <SignUpForm />
      </main>
      <Footer />
    </div>
  );
}
