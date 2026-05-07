export default function Loading() {
  return (
    <div className="grid min-h-screen place-items-center bg-brand-black">
      <div className="text-center">
        <p className="display-font text-6xl text-brand-red md:text-7xl">SANTI WRLD</p>
        <p className="mt-2 text-sm uppercase tracking-[0.3em] text-zinc-300">Loading drop...</p>
        <div className="mx-auto mt-5 h-1 w-40 overflow-hidden rounded bg-zinc-800">
          <div className="h-full w-1/2 animate-pulse bg-brand-red" />
        </div>
      </div>
    </div>
  );
}
