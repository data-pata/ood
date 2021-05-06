package pos.dataobjects;

import java.util.List;
    
public final class SaleDTO {
    private final double totalSum;
    private final List<LineItemDTO> LineItemDTOList;
    private final double totalVAT;

    public SaleDTO(double runningTotal, List<LineItemDTO> LineItemsDTO, double totalVAT) {
        this.totalSum = runningTotal;
        this.LineItemDTOList = LineItemsDTO;
        this.totalVAT = totalVAT;
    }

    public double getTotalSum() {
        return this.totalSum;
    }

    public List<LineItemDTO> getLineItemsDTO() {
        return this.LineItemDTOList;
    }

    public double getTotalVAT() {
        return this.totalVAT;
    }

}