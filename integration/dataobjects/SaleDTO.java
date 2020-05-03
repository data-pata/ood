package integration.dataobjects;

import java.util.List;

public class SaleDTO {
    private final double totalSum;
    private final List<LineItemDTO> LineItemDTOList;

    public SaleDTO(double runningTotal, List<LineItemDTO> LineItemsDTO) {
        this.totalSum = runningTotal;
        this.LineItemDTOList = LineItemsDTO;
    }

    public double getTotalSum() {
        return this.totalSum;
    }

    public List<LineItemDTO> getLineItemsDTO() {
        return this.LineItemDTOList;
    }

}