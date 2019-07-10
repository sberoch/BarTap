package com.eriochrome.bartime.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.ComprobanteDeCompra;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class ComprobanteCompraHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    private ComprobanteDeCompra comprobanteDeCompra;

    private TextView descripcion;
    private TextView nombreBar;
    private TextView costo;
    private TextView nroComprobante;



    public interface OnComprobanteClickListener {
        void onClick(ComprobanteDeCompra comprobanteDeCompra);
    }
    private OnComprobanteClickListener clickListener;

    public ComprobanteCompraHolder(View view, Context context, boolean esBar) {
        super(view);
        this.context = context;

        descripcion = view.findViewById(R.id.desc);
        nombreBar = view.findViewById(R.id.nombre_bar);
        costo = view.findViewById(R.id.costo);
        nroComprobante = view.findViewById(R.id.nro_comprobante);

        if (esBar) {
            try {
                clickListener = (OnComprobanteClickListener) context;
            } catch (ClassCastException e) {
                toastShort(context, "No se implemento la interfaz");
            }

            view.setOnClickListener(this);
        }
    }

    public void bind(ComprobanteDeCompra comprobanteDeCompra) {

        this.comprobanteDeCompra = comprobanteDeCompra;

        descripcion.setText(comprobanteDeCompra.getDescripcion());
        nombreBar.setText(comprobanteDeCompra.getNombreBar());
        String costoText = "Costo: " + comprobanteDeCompra.getCosto();
        costo.setText(costoText);
        String nroComprobanteText = "Nro. Comprobante: " + comprobanteDeCompra.getNroComprobante();
        nroComprobante.setText(nroComprobanteText);
    }

    @Override
    public void onClick(View v) {
        clickListener.onClick(comprobanteDeCompra);
    }
}
