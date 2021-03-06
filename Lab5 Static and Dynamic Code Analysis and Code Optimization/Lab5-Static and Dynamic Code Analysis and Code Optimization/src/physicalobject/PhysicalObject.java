package physicalobject;

/**.
 * {@code PhysicalObject} 是一个可放置/分配到中心点或轨道的具体事物。一个多轨道 系统中可承载多个物体。物体只能出现在中心点或轨道
 * 上， 不能出现在其他位置。 例如在太阳系里，“太阳”在中心点，八颗行星分处于 不同的轨道上。在径赛场地，每条跑道 被分配给最多一
 * 个“运动员”。在原子模型中，原子核周围的每条电子 轨道上可能有多个电子。 有些应用中，物体需 要区分（例如太阳系的八颗行星是
 * 不同的、竞赛比赛中的运动员是不同的）。有些应用中，物体无需区分（例如 在原子结构中，各轨道上的电子可 看作都是一样的）。
 *
 */
public abstract class PhysicalObject {

  /**.
   * 获得该物体的名称
   * 
   * @return 物体名称
   */
  public abstract String getName();
}
